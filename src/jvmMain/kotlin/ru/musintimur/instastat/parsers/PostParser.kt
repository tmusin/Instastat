package ru.musintimur.instastat.parsers

import kotlinx.coroutines.delay
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import ru.musintimur.instastat.extensions.errorLog
import ru.musintimur.instastat.extensions.getDateTimeFormatter
import ru.musintimur.instastat.extensions.log
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.repository.Repository
import java.time.LocalDateTime

private const val postDateBlockClassName = "_1o9PC"
private const val commentButtonClassName = "dCJp8"
private const val commentEntryClassName = "Mr508"
private const val commentDivClassName = "C4VMK"


suspend fun parsePost(db: Repository, post: Post) {
    val postUrl = post.postUrl.trim()
    "\nЧтение поста: $postUrl".log()
    val selDriver = Selenium.seleniumDriver
    val wait = Selenium.seleniumWait

    try {
        selDriver.get(postUrl)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(postDateBlockClassName)))
        val dateBlock = selDriver.findElementByClassName(postDateBlockClassName)
        val postDateTimeString = dateBlock.getAttribute("datetime").take(19).replace('T', ' ')
        val postDateTime = LocalDateTime.parse(postDateTimeString, getDateTimeFormatter())
        val text = selDriver.title.substringAfter('“').substringBeforeLast('”')
        db.posts.updateDateTime(postUrl, postDateTime)
        db.posts.updateText(postUrl, text)

        var commentsButton = selDriver.findElements(By.className(commentButtonClassName))
        "Грузим комментарии...".log()
        saveComments(selDriver, db, post)
        while (commentsButton.isNotEmpty() && commentsButton.first().isDisplayed) {
            wait.until(elementToBeClickable(commentsButton.first()))
            commentsButton.first().click()
            delay(3000)
            commentsButton = selDriver.findElements(By.className(commentButtonClassName))
            "Ищем еще комментарии...".log()
            saveComments(selDriver, db, post)
        }

        val commentsCount = db.comments.calculateComments(post)
        "Найдено комментариев верхнего уровня: $commentsCount".log()
        db.posts.updateCommentsCount(postUrl, commentsCount)

    } catch (e: Exception) {
        "Ошибка при обработке поста ${postUrl}: ${e.message}".errorLog()
    }
}

private suspend fun saveComments(selDriver: FirefoxDriver, db: Repository, post: Post) {
    val comments = selDriver.findElements(By.className(commentEntryClassName))
    comments.forEach { comment ->
        var commentAuthor = ""
        var commentText = ""
        comment.findElement(By.className(commentDivClassName))
            .findElements(By.tagName("span"))
            .forEach {
                if (it.getAttribute("class").isBlank()) {
                    commentText = it.text
                } else {
                    commentAuthor = it.text
                }
            }
        val oldComment = db.comments.findComment(post, commentAuthor, commentText)
        if (oldComment == null) {
            db.comments.addNewComment(post, commentAuthor, commentText)
        }
    }
}