package dev.postnotifier.infrastructure.api.controller.admin

import dev.postnotifier.annotation.RequestValidation
import dev.postnotifier.infrastructure.api.request.UserUpsertRequest
import dev.postnotifier.infrastructure.api.response.UserResponse
import dev.postnotifier.infrastructure.api.response.UsersResponse
import dev.postnotifier.usecase.command.CreateUserCommand
import dev.postnotifier.usecase.command.DeleteUserCommand
import dev.postnotifier.usecase.query.GetUserQuery
import dev.postnotifier.usecase.query.GetUsersQuery
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.*

/**
 * ユーザーコントローラー
 */
@Tag(name = "User", description = "ユーザー")
@Controller("/api/admin/users", produces = [MediaType.APPLICATION_JSON])
@Secured("ROLE_ADMIN")
open class AdminUserRestController(
    private val getUserQuery: GetUserQuery,
    private val getUsersQuery: GetUsersQuery,
    private val createUserCommand: CreateUserCommand,
    private val deleteUserCommand: DeleteUserCommand
) {

    /**
     * ユーザー取得API
     *
     * @param userId 顧客ID
     * @param authentication 認証情報
     * @return ユーザー
     */
    @Get("/{user_id}")
    @Status(HttpStatus.OK)
    fun getUser( //
        @PathVariable("user_id") userId: UUID, //
        @Parameter(hidden = true) authentication: Authentication
    ): UserResponse {
        return UserResponse(getUserQuery.handle(userId))
    }

    /**
     * ユーザーリスト取得API
     *
     * @param authentication 認証情報
     * @return ユーザーリスト
     */
    @Get
    @Status(HttpStatus.OK)
    fun getUser(@Parameter(hidden = true) authentication: Authentication): UsersResponse {
        val users = getUsersQuery.handle().map {
            UserResponse(it)
        }.toList()
        return UsersResponse(users)
    }

    /**
     * ユーザー作成API
     *
     * @param requestBody ユーザー作成リクエスト
     * @param authentication 認証情報
     */
    @Post
    @Status(HttpStatus.OK)
    open fun createUser(
        @Body @RequestValidation requestBody: UserUpsertRequest,
        @Parameter(hidden = true) authentication: Authentication
    ) {
        createUserCommand.execute(requestBody)
    }

    /**
     * ユーザー削除API
     *
     * @param userId ユーザーID
     * @param authentication 認証情報
     */
    @Delete("/{user_id}")
    @Status(HttpStatus.OK)
    fun deleteUser(
        @PathVariable("user_id") userId: UUID,
        @Parameter(hidden = true) authentication: Authentication
    ) {
        deleteUserCommand.execute(userId, authentication)
    }
}
