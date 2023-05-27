package com.example.configurations

import com.example.data.tables.MetadataTable
import com.example.data.tables.ResultTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

fun Application.configureDatabases() {
    val hikariConfig = getHikariConfig()
    val hikariDataSource = HikariDataSource(hikariConfig)

    Database.connect(datasource = hikariDataSource)
    runFlyway(datasource = hikariDataSource)

    transaction {
        SchemaUtils.drop(
            ResultTable,
            MetadataTable
        )
        SchemaUtils.create(
            ResultTable,
            MetadataTable
        )
    }
}

context (Application)
fun getHikariConfig() = HikariConfig().apply {
    isAutoCommit = true

    jdbcUrl = fetchProperty("ktor.dataSource.databasePath").getString()
    poolName = fetchProperty("ktor.dataSource.poolName").getString()

    minimumIdle = fetchProperty("ktor.dataSource.minimumIdle").getString().toInt()
    driverClassName = fetchProperty("ktor.dataSource.databaseDriver").getString()

    maximumPoolSize = fetchProperty("ktor.dataSource.poolSize").getString().toInt()
    connectionTestQuery = fetchProperty("ktor.dataSource.connectionTestQuery").getString()

    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    validate()
}

context (Application)
fun fetchProperty(propertyName: String) = environment.config.property(propertyName)

fun runFlyway(datasource: DataSource) {
    val flyway = Flyway
        .configure()
        .baselineOnMigrate(true)
        .dataSource(datasource)
        .load()
    try {
        flyway.info()
        flyway.migrate()
    } catch (e: Exception) {
        throw e
    }
}

