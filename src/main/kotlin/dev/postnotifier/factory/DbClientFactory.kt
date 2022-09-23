package dev.postnotifier.factory

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

@Factory
class DbClientFactory(private val dataSource: DataSource) {

    @Singleton
    fun getDbClient(): Database {
        return Database.connect(dataSource)
    }

}
