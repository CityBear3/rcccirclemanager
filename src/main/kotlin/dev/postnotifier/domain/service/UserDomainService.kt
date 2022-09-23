package dev.postnotifier.domain.service

import dev.postnotifier.domain.model.UserModel
import dev.postnotifier.domain.repository.UserRepository
import jakarta.inject.Singleton

@Singleton
class UserDomainService(private val userRepository: UserRepository) {

    fun checkIsEmailExist(userModel: UserModel): Boolean {
        if (userRepository.findByEmail(userModel.email) == null) {
            return false
        }
        return true
    }

}
