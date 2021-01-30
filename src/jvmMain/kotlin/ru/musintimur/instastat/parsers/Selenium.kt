package ru.musintimur.instastat.parsers

import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.musintimur.instastat.common.constants.ENV_FIREFOX_BIN_PATH
import ru.musintimur.instastat.common.constants.ENV_GECKO_BIN_PATH
import ru.musintimur.instastat.extensions.log
import java.io.File

object Selenium {

    lateinit var seleniumDriver: FirefoxDriver
    lateinit var seleniumWait: WebDriverWait

    fun initSelenium() {
        "\nЗапускаем Selenium Web Driver...".log()
        val pathBinary = FirefoxBinary(File(System.getenv("FIREFOX_BIN_PATH")))
        System.setProperty("webdriver.gecko.driver", System.getenv("GECKO_BIN_PATH"))
        val ffOptions = FirefoxOptions().apply {
            binary = pathBinary
            setHeadless(true)
            addPreference("javascript.enabled", true)
        }
        seleniumDriver = FirefoxDriver(ffOptions)
        seleniumWait = WebDriverWait(seleniumDriver, 10L)
    }

    fun closeSelenium() {
        "\nЗакрываем Selenium Web Driver..".log()
        seleniumDriver.quit()
    }

}