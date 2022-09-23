package dev.postnotifier.infrastructure.api.response

import io.micronaut.core.annotation.Introspected

/**
 * ユーザーリストレスポンス
 */
@Introspected
data class UsersResponse(
    /**
     * ユーザーリスト
     */
    val users: List<UserResponse>
)
