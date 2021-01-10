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

const val statBlockClassName = "k9GMp"
const val statLiClassName = "Y8-fY"
const val statSpanClassName = "g47SY"

suspend fun parsePage(db: Repository, profile: Profile) {
    "\nParsing profile: ${profile.profileName}".log()
    val profileUrl = getFullProfileUrl(profile.profileName)
    val selDriver = Selenium.seleniumDriver
    val wait = Selenium.seleniumWait

    try {
        selDriver.get(profileUrl)
        wait.until(presenceOfElementLocated(By.className(statBlockClassName)))
        val statBlock = selDriver.findElementByClassName(statBlockClassName)
        val stats = statBlock.findElements(By.tagName("li"))
        if (stats.isNotEmpty()) {
            val statistics = Statistics(0, 0, 0)
            stats.forEach { li ->
                val aTag = li.findElement(By.tagName("a"))
                val staType = getStatType(aTag)
                val spanTag = aTag.findElement(By.tagName("span"))
                val spanText = spanTag.text
                var count = 0L
                runCatching {
                    spanText.parseLong()
                }.onSuccess {
                    count = it
                }.onFailure { error ->
                    "Fail 1: ${error.message}".errorLog()
                    runCatching {
                        spanTag.getAttribute("title").parseLong()
                    }.onSuccess {
                        count = it
                    }.onFailure {
                        "Fail 2: ${it.message}".errorLog()
                    }
                }
                "$count ${staType.name}".log()
                when (staType) {
                    StatType.Publications -> statistics.posts = count
                    StatType.Followers -> statistics.followers = count
                    StatType.Followings -> statistics.followings = count
                }
            }
            withContext(Dispatchers.IO) {
                db.profilesHistory.insertNewStatistics(profile, statistics)
            }
        } else {
            "Spans not found!".log()
        }
    } catch (e: Exception) {
        "Exception on parsing ${profile.profileName}: ${e.message}".errorLog()
    }
}