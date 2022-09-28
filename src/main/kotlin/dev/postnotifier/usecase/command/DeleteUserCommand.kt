package dev.postnotifier.usecase.command

import dev.postnotifier.domain.repository.UserRepository
import dev.postnotifier.exception.ErrorCode
import dev.postnotifier.exception.NotFoundException
import dev.postnotifier.exception.UnexpectedException
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import java.util.*

@Singleton
class DeleteUserCommand(
    private val userRepository: UserRepository
) {

    fun execute(userId: UUID, authentication: Authentication) {
        // ユーザーの存在チェック
        val user = (userRepository.findById(userId)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER))

        // 管理者自身が自らを削除することを防止
        if (!user.checkIsDeletable(UUID.fromString(authentication.name))) {
            throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR)
        }

        userRepository.delete(user.id!!)
    }

}
