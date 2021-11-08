package ru.musintimur.instastat.database

import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.postgresql.ds.PGSimpleDataSource
import ru.musintimur.instastat.common.constants.*
import javax.sql.DataSource

private val DB_HOST = runCatching {
    System.getenv(ENV_SERVER_HOST)
}.getOrDefault("localhost")

private val DB_PORT = runCatching {
    System.getenv(ENV_SERVER_PORT).toInt()
}.getOrDefault(5432)

private val DB_NAME = runCatching {
    System.getenv(ENV_DATABASE_NAME)
}.getOrDefault("instastat_db")

private val DB_SCHEMA = runCatching {
    System.getenv(ENV_SCHEMA)
}.getOrDefault("instastat")

private val DB_USERNAME = runCatching {
    System.getenv(ENV_DB_USER)
}.onFailure {
    println("Invalid database username.")
}.getOrThrow()

private val DB_PASSWORD = runCatching {
    System.getenv(ENV_DB_PASSWORD)
}.onFailure {
    println("Invalid database password.")
}.getOrThrow()

@ExperimentalCoroutinesApi
fun getInstastatDatabaseDriver(): JdbcDriver {
    val dataSource: DataSource = PGSimpleDataSource().apply {
        serverNames = arrayOf(DB_HOST)
        portNumbers = intArrayOf(DB_PORT)
        databaseName = DB_NAME
        currentSchema = DB_SCHEMA
        user = DB_USERNAME
        password = DB_PASSWORD
    }
    val driver = dataSource.asJdbcDriver()
    InstastatDatabase.Schema.create(driver)
    return driver
}