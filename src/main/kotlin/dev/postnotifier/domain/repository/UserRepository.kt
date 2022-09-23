package dev.postnotifier.domain.repository

import dev.postnotifier.domain.model.UserModel

interface UserRepository {

    fun findByEmail(email: String): UserModel?

    fun create(userModel: UserModel)

}
