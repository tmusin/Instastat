package ru.musintimur.instastat.web.links

import ru.musintimur.instastat.common.constants.*

private const val SCRIPTS_STATIC_FOLDER = "scripts"
private const val ICONS_STATIC_FOLDER = "icons"
private const val STYLES_STATIC_FOLDER = "styles"

fun getRootStaticFolder(): String = System.getenv(ENV_INSTASTAT_STATIC_FOLDER)
fun getStaticScriptsFolder() = "${getRootStaticFolder()}${java.io.File.separator}$SCRIPTS_STATIC_FOLDER"
fun getStaticIconsFolder() = "${getRootStaticFolder()}${java.io.File.separator}$ICONS_STATIC_FOLDER"
fun getStaticStylesFolder() = "${getRootStaticFolder()}${java.io.File.separator}$STYLES_STATIC_FOLDER"