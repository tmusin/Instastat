import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("multiplatform") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("com.squareup.sqldelight") version "1.4.4"
    application
}

group = "ru.musintimur"
version = "1.002"
val mainServerClassName = "ru.musintimur.instastat.MainAppKt"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                val ktorVersion = "1.4.0"
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-html-builder:$ktorVersion")
                implementation("io.ktor:ktor-locations:$ktorVersion")
                implementation("io.ktor:ktor-gson:$ktorVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("org.jetbrains:kotlin-css:1.0.0-pre.111-kotlin-1.4.0")

                val sqlDelightVersion = "1.4.4"
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions-jvm:$sqlDelightVersion")

                implementation("org.seleniumhq.selenium:selenium-java:3.141.59")

                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
            sqldelight {
                database("InstastatDatabase") {
                    packageName = "ru.musintimur.instastat.database"
                }
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains:kotlin-react:16.13.1-pre.113-kotlin-1.4.0")
                implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.113-kotlin-1.4.0")
                implementation(npm("chart.js", "2.9.4"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

val distributionsFolder = File(project.buildDir, "distributions")

val resourcesPath = "${project.buildDir}/processedResources"

val resourcesJvm = "${resourcesPath}/jvm/main"
val resourcesJvmIcons = File(resourcesJvm, "icons")

val resourcesJs = "${resourcesPath}/js/main"
val resourcesJsLibs = File(resourcesJs, "libs")

val webFolder = "web"
val scriptsFolder = "$webFolder/scripts"
val iconsFolder = "$webFolder/icons"
val stylesFolder = "$webFolder/styles"

val libsFolder = "libs"
val libsScriptsFolder = File(project.buildDir, "$libsFolder/$scriptsFolder")
val libsIconsFolder = File(project.buildDir, "$libsFolder/$iconsFolder")
val libsStylesFolder = File(project.buildDir, "$libsFolder/$stylesFolder")

val databaseFolder = "db_instastat"

val absolutStaticPath = File(project.buildDir, "$libsFolder/$webFolder")
val absolutDatabasePath = File(project.projectDir, databaseFolder)

application {
    mainClassName = mainServerClassName
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "output.js"
}

tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
    doLast {
        copy {
            from(distributionsFolder) {
                include("*.js", "*.js.map")
            }
            into(libsScriptsFolder)
        }
        copy {
            from(resourcesJvmIcons) {
                include("*.png")
            }
            into(libsIconsFolder)
        }
        copy {
            from(resourcesJsLibs) {
                include("*.js", "*.js.map")
            }
            into(libsScriptsFolder)
        }
        copy {
            from(resourcesJsLibs) {
                include("*.css")
            }
            into(libsStylesFolder)
        }
    }
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(
        tasks.getByName<Jar>("jvmJar")
    )

    val envs = mutableMapOf(
        "INSTASTAT_STATIC_FOLDER" to absolutStaticPath,
        "INSTASTAT_DB_CATALOG" to absolutDatabasePath,
        "FIREFOX_BIN_PATH" to "/usr/bin/firefox",
        "GECKO_BIN_PATH" to "/usr/bin/geckodriver",
        "LOGGING" to "true",
        "ERROR_LOGGING" to "true"
    )

    val secretsFile = File("${project.rootDir}/secrets.credentials")
    secretsFile.forEachLine { line ->
        val property = line.split('=', limit = 2)
        if (property.size == 2) envs[property[0]] = property[1]
    }

    environment(envs)
}

tasks.withType<ShadowJar> {
    group = "shadow"
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    manifest {
        attributes(
            Pair("Main-Class", mainServerClassName)
        )
    }
    exclude("icons")
}