package dev.postnotifier.infrastructure.api.request

import dev.postnotifier.exception.BadRequestException
import dev.postnotifier.exception.ErrorCode
import io.micronaut.core.annotation.Introspected

@Introspected
class UserUpsertRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val isAdmin: Boolean
) : BaseRequest() {

    override fun validate() {
        if (firstName.length > 255) {
            throw BadRequestException(ErrorCode.INVALID_FIRST_NAME)
        }

        if (lastName.length > 255) {
            throw BadRequestException(ErrorCode.INVALID_LAST_NAME)
        }
    }

}
