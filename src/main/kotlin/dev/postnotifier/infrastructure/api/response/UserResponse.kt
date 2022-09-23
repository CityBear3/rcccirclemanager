package dev.postnotifier.infrastructure.api.response


import dev.postnotifier.usecase.result_dto.UserResultDto
import io.micronaut.core.annotation.Introspected
import java.util.*

/**
 * ユーザーレスポンス
 */
@Introspected
data class UserResponse(
    /**
     * ユーザーID
     */
    val id: UUID,

    /**
     * メールアドレス
     */
    val email: String,

    /**
     * ファーストネーム
     */
    val firstName: String,

    /**
     * ラストネーム
     */
    val lastName: String,

    /**
     * 管理者フラグ
     */
    val isAdmin: Boolean

) {
    constructor(userResultDto: UserResultDto) : this(
        userResultDto.id,
        userResultDto.email,
        userResultDto.firstName,
        userResultDto.lastName,
        userResultDto.isAdmin
    )
}
