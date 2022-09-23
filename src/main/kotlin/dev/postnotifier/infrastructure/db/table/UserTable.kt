package dev.postnotifier.infrastructure.db.table

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable("users") {
    val email = varchar("email", 255)

    val firstName = varchar("first_name", 255)

    val lastName = varchar("last_name", 255)

    val password = varchar("password", 255)

    val isAdmin = bool("is_admin")
}
