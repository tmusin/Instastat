package ru.musintimur.instastat.parsers

import org.openqa.selenium.WebElement
import ru.musintimur.instastat.common.extensions.replaceHtmlMnemonics
import ru.musintimur.instastat.model.StatType
import java.net.URL

fun getFullProfileUrl(profileName: String): String =
    "https://www.instagram.com/$profileName/"

fun getPageHtml(url: String) =
    URL(url).readText().replaceHtmlMnemonics()

fun getStatType(html: WebElement): StatType {
    val hrefText = html.getAttribute("href")
    return when {
        hrefText.contains("source=profile_posts") -> StatType.Publications
        hrefText.contains("source=followed_by_list") -> StatType.Followers
        hrefText.contains("source=follows_list") -> StatType.Followings
        else -> throw IllegalArgumentException("Illegal web element.")
    }
}