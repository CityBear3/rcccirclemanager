package dev.postnotifier.domain.repository

import dev.postnotifier.domain.model.UserModel
import java.util.UUID

interface UserRepository {

    fun findByEmail(email: String): UserModel?

    fun findById(id: UUID): UserModel?

    fun create(userModel: UserModel)

    fun delete(id: UUID)

}
