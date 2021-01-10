package ru.musintimur.instastat.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.musintimur.instastat.common.constants.ENV_INSTASTAT_DB_CATALOG
import java.io.File

private const val DB_NAME = "InstastatDatabase"

private val dbCatalog = runCatching {
    System.getenv(ENV_INSTASTAT_DB_CATALOG)
}.getOrDefault(System.getProperty("java.io.tmpdir"))

@ExperimentalCoroutinesApi
@Suppress("FunctionName")
fun getInstastatDatabaseDriver(): SqlDriver {
    val databasePath = File(dbCatalog, "$DB_NAME.db")
    val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
    InstastatDatabase.Schema.create(driver)

    return driver
}