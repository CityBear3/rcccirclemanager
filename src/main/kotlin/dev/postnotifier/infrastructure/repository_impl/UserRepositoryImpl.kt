package dev.postnotifier.infrastructure.repository_impl

import dev.postnotifier.domain.model.UserModel
import dev.postnotifier.domain.repository.UserRepository
import dev.postnotifier.infrastructure.db.table.UserTable
import dev.postnotifier.util.AuthUtil
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class UserRepositoryImpl(
    private val database: Database,
    private val authUtil: AuthUtil
) : UserRepository {

    override fun findByEmail(email: String): UserModel? {
        return transaction {
            val user = UserTable.select {
                UserTable.email eq email
            }.map {
                UserModel(
                    it[UserTable.id].value,
                    it[UserTable.email],
                    it[UserTable.firstName],
                    it[UserTable.lastName],
                    it[UserTable.password],
                    it[UserTable.isAdmin]
                )
            }.firstOrNull()
            return@transaction user
        }
    }

    override fun create(userModel: UserModel) {
        transaction {
            UserTable.insert {
                it[email] = userModel.email
                it[firstName] = userModel.firstName
                it[lastName] = userModel.lastName
                it[password] = authUtil.encode(userModel.password)
                it[isAdmin] = userModel.isAdmin
            }
        }
    }

}
