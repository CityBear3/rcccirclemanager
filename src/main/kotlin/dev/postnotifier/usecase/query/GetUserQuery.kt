package dev.postnotifier.usecase.query


import dev.postnotifier.exception.ErrorCode
import dev.postnotifier.exception.NotFoundException
import dev.postnotifier.infrastructure.db.table.UserTable
import dev.postnotifier.usecase.result_dto.UserResultDto
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@Singleton
class GetUserQuery(private val database: Database) {

    /**
     * ユーザーを取得
     *
     * @param id ユーザーID
     *
     * @return ユーザー
     */
    fun handle(id: UUID): UserResultDto {
        return transaction {
            return@transaction UserTable.select {
                UserTable.id eq id
            }.map {
                UserResultDto(
                    it[UserTable.id].value,
                    it[UserTable.email],
                    it[UserTable.firstName],
                    it[UserTable.lastName],
                    it[UserTable.isAdmin]
                )
            }.firstOrNull() ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER)
        }
    }
}

