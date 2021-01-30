package ru.musintimur.instastat.parsers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.openqa.selenium.By
import ru.musintimur.instastat.extensions.log
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import ru.musintimur.instastat.common.extensions.parseLong
import ru.musintimur.instastat.extensions.errorLog
import ru.musintimur.instastat.model.StatType
import ru.musintimur.instastat.model.entities.Profile
import ru.musintimur.instastat.model.entities.Statistics
import ru.musintimur.instastat.repository.Repository

private const val statBlockClassName = "k9GMp"
private const val statPropertyClassName = "-nal3"
private const val statLiClassName = "Y8-fY"
private const val statSpanClassName = "g47SY"

suspend fun parsePage(db: Repository, profile: Profile) {
    "\nЧтение аккаунта: ${profile.profileName}".log()
    val profileUrl = getFullProfileUrl(profile.profileName)
    val selDriver = Selenium.seleniumDriver
    val wait = Selenium.seleniumWait

    try {
        selDriver.get(profileUrl)
        wait.until(presenceOfElementLocated(By.className(statBlockClassName)))
        val statBlock = selDriver.findElementByClassName(statBlockClassName)
        val stats = statBlock.findElements(By.className(statLiClassName))
        if (stats.isNotEmpty()) {
            val statistics = Statistics(0, 0, 0)
            stats.forEach { li ->
                val property = li.findElement(By.className(statPropertyClassName))
                val statType = if (property.tagName == "span") StatType.Publications else getStatType(property)
                val spanTag = property.findElement(By.className(statSpanClassName))
                val spanText = spanTag.text
                var count = 0L
                runCatching {
                    spanText.parseLong()
                }.onSuccess {
                    count = it
                }.onFailure { error ->
                    "Некритичная ошибка 1: ${error.message}".errorLog()
                    runCatching {
                        spanTag.getAttribute("title").parseLong()
                    }.onSuccess {
                        count = it
                    }.onFailure {
                        "Некритичная ошибка 2: ${it.message}".errorLog()
                    }
                }
                "$count ${statType.name}".log()
                when (statType) {
                    StatType.Publications -> statistics.posts = count
                    StatType.Followers -> statistics.followers = count
                    StatType.Followings -> statistics.followings = count
                }
            }
            withContext(Dispatchers.IO) {
                db.profilesHistory.insertNewStatistics(profile, statistics)
            }
        } else {
            "Не найдены ожидаемые блоки страницы!".log()
        }
    } catch (e: Exception) {
        "Ошибка при обработке аккаунта ${profile.profileName}: ${e.message}".errorLog()
    }
}