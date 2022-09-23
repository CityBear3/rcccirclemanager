package dev.postnotifier.usecase.query

import dev.postnotifier.infrastructure.db.table.UserTable
import dev.postnotifier.usecase.result_dto.UserResultDto
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
class GetUsersQuery(private val database: Database) {

    /**
     * ユーザーリストを取得
     *
     * @return ユーザーリスト
     */
    fun handle(): List<UserResultDto> {
        return transaction {
            return@transaction UserTable.selectAll() //
                .map {
                    UserResultDto(
                        it[UserTable.id].value,
                        it[UserTable.email],
                        it[UserTable.firstName],
                        it[UserTable.lastName],
                        it[UserTable.isAdmin]
                    )
                }.toList()
        }
    }

}
