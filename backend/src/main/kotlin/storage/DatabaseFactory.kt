package storage

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.EnvConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import storage.db.Favorites
import storage.db.Reminders

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = EnvConfig.dbUrl
            driverClassName = "org.postgresql.Driver"
            username = EnvConfig.dbUser
            password = EnvConfig.dbPassword
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                Reminders,
                Favorites
            )
        }
    }
}