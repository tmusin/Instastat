package ru.musintimur.instastat.parsers

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import ru.musintimur.instastat.extensions.errorLog
import ru.musintimur.instastat.extensions.log

private val username: String = runCatching {
    System.getenv("INSTAGRAM_USERNAME")
}.getOrThrow()

private val password: String = runCatching {
    System.getenv("INSTAGRAM_PASSWORD")
}.getOrThrow()

private const val loginFormClass = "HmktE"
private const val userNameInputName = "username"
private const val passwordInputName = "password"
private const val notNowButtonClass = "cmbtv"

private const val avatarClass = "qNELH"
private const val menuClass = "_7UhW9"
private const val scriptDiv = "oZBNB"

fun doAuth() {
    "\nВыполняется вход в профиль под учетной записью $username...".log()
    val selDriver = Selenium.seleniumDriver
    val wait = Selenium.seleniumWait

    try {
        selDriver.get("https://www.instagram.com/")
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(loginFormClass)))
        val loginForm = selDriver.findElementByClassName(loginFormClass)
        loginForm.findElement(By.name(userNameInputName)).sendKeys(username)
        loginForm.findElement(By.name(passwordInputName)).sendKeys(password)
        loginForm.submit()
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(avatarClass)))
    } catch (e: Exception) {
        "Возникла ошибка при попытке войти в профиль: ${e.message}".errorLog()
    }

    try {
        "Проверка на наличие формы подтверждения...".log()
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(notNowButtonClass)))
        val div = selDriver.findElementByClassName(notNowButtonClass)
        div.findElement(By.ByTagName("button")).click()
    } catch (e: Exception) {
        "Подтверждение не требуется: ${e.message}".errorLog()
    }
}

fun doLogout() {
    "\nВыходим из профиля...".log()
    val selDriver = Selenium.seleniumDriver
    val wait = Selenium.seleniumWait

    try {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(avatarClass)))

        val avatar = selDriver.findElementByClassName(avatarClass)
        avatar.click()

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(scriptDiv)))

        val divs = avatar
            .findElement(By.xpath("./.."))
            .findElements(By.className(menuClass))

        val exitButton = divs.first { it.text in setOf("Выйти", "Log Out") }
        exitButton.click().also { "Выход произведён успешно.".log() }
    } catch (e: Exception) {
        "Возникла ошибка при попытке выйти из профиля: ${e.message}".errorLog()
    }
}