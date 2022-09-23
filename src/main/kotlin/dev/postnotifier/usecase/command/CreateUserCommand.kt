package dev.postnotifier.usecase.command

import dev.postnotifier.domain.model.UserModel
import dev.postnotifier.domain.repository.UserRepository
import dev.postnotifier.domain.service.UserDomainService
import dev.postnotifier.exception.BadRequestException
import dev.postnotifier.exception.ConflictException
import dev.postnotifier.exception.ErrorCode
import dev.postnotifier.infrastructure.api.request.UserUpsertRequest
import jakarta.inject.Singleton

@Singleton
class CreateUserCommand(
    private val userDomainService: UserDomainService,
    private val userRepository: UserRepository
) {

    fun execute(requestBody: UserUpsertRequest) {
        val user = UserModel(
            email = requestBody.email,
            firstName = requestBody.firstName,
            lastName = requestBody.lastName,
            password = requestBody.password,
            isAdmin = requestBody.isAdmin
        )

        // バリデーション
        if (userDomainService.checkIsEmailExist(user)) {
            throw ConflictException(ErrorCode.EMAIL_IS_USED)
        }
        if (!user.checkEmail()) {
            throw BadRequestException(ErrorCode.INVALID_EMAIL)
        }
        if (!user.checkPassword()) {
            throw BadRequestException(ErrorCode.INVALID_PASSWORD)
        }

        userRepository.create(user)
    }

}
