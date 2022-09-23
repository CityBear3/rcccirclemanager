package dev.postnotifier.infrastructure.api.controller.admin

import dev.postnotifier.exception.ErrorCode
import dev.postnotifier.infrastructure.api.controller.BaseRestControllerTest
import dev.postnotifier.infrastructure.api.request.UserUpsertRequest
import dev.postnotifier.infrastructure.api.response.UserResponse
import dev.postnotifier.infrastructure.api.response.UsersResponse
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class AdminUserRestControllerTest : BaseRestControllerTest() {
    // API PATH
    private val basePath = "/api/admin/users"


    @Test
    @DisplayName("ユーザー取得API ユーザーを取得できる")
    fun testGetUserAPI_Success() {
        val cookie = loginAdmin()
        val url = basePath + "/${admin.id}"
        val request = HttpRequest.GET<String>(url).cookie(cookie)
        val response = client.toBlocking().retrieve(request, UserResponse::class.java)
        Assertions.assertTrue { Objects.nonNull(response) }
        Assertions.assertTrue { response.id == admin.id }
        Assertions.assertTrue { response.email == admin.email }
    }

    @Test
    @DisplayName("ユーザー取得API ユーザーが存在しない場合は404エラー")
    fun testGetUserAPI_FailedByNotFoundUser() {
        val cookie = loginAdmin()
        val url = basePath + "/${UUID.randomUUID()}"
        val request = HttpRequest.GET<String>(url).cookie(cookie)
        try {
            client.toBlocking().retrieve(request, UserResponse::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertTrue { exception.status == HttpStatus.NOT_FOUND }
        }
    }

    @Test
    @DisplayName("ユーザー取得API 管理者以外の場合は403エラー")
    fun testGetUserAPI_FailedByUserHasNoPermission() {
        val cookie = login()
        val url = basePath + "/${admin.id}"
        val request = HttpRequest.GET<String>(url).cookie(cookie)
        try {
            client.toBlocking().retrieve(request, UserResponse::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertTrue { exception.status == HttpStatus.FORBIDDEN }
        }
    }

    @Test
    @DisplayName("ユーザー取得API ログインしていない場合は401エラー")
    fun testGetUserAPI_FailedByUserNotLogin() {
        val url = basePath + "/${admin.id}"
        val request = HttpRequest.GET<String>(url)
        try {
            client.toBlocking().retrieve(request, UserResponse::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertTrue { exception.status == HttpStatus.UNAUTHORIZED }
        }
    }

    @Test
    @DisplayName("ユーザーリスト取得API ユーザーリスト取得できる")
    fun testGetUsersAPI_Success() {
        val cookie = loginAdmin()
        val url = basePath
        val request = HttpRequest.GET<String>(url).cookie(cookie)
        val response = client.toBlocking().retrieve(request, UsersResponse::class.java)
        Assertions.assertTrue { Objects.nonNull(response) }
        Assertions.assertTrue { response.users[0].id == admin.id }
        Assertions.assertTrue { response.users[0].email == admin.email }
    }

    @Test
    @DisplayName("ユーザーリスト取得API 管理者以外の場合は403エラー")
    fun testGetUsersAPI_FailedByUserHasNoPermission() {
        val cookie = login()
        val url = basePath
        val request = HttpRequest.GET<String>(url).cookie(cookie)
        try {
            client.toBlocking().retrieve(request, UsersResponse::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertTrue { exception.status == HttpStatus.FORBIDDEN }
        }
    }

    @Test
    @DisplayName("ユーザーリスト取得API ログインしていない場合は401エラー")
    fun testGetUsersAPI_FailedByUserNotLogin() {
        val url = basePath
        val request = HttpRequest.GET<String>(url)
        try {
            client.toBlocking().retrieve(request, UsersResponse::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertTrue { exception.status == HttpStatus.UNAUTHORIZED }
        }
    }

    @Test
    @DisplayName("ユーザー作成API ユーザーを作成できる")
    fun testUserCreateAPI_Success() {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test", "test", "Test-1234", false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        val result = connection.prepareStatement(
            """
            SELECT * FROM users WHERE users.email = '${userUpsertRequest.email}'
        """.trimIndent()
        ).executeQuery()
        result.next()
        Assertions.assertEquals(userUpsertRequest.email, result.getString("email"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["@dummy", ".test@dummy.com", "te..st@dummu.com", "test.@dummy@dummy.com"])
    @DisplayName("ユーザー作成API 不正なメールアドレスの場合は400エラー")
    fun testUserCreateAPI_FailedByInvalidEmail(email: String) {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest(email, "test", "test", "Test-1234", false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            Assertions.assertEquals(ErrorCode.INVALID_EMAIL.message, exception.message)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Ab-cd", "ab-cd", "ab@cd", "Abc", "Abc-def"])
    @DisplayName("ユーザー作成API 不正なパスワードの場合は400エラー")
    fun testUserCreateAPI_FailedByInvalidPassword(password: String) {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test", "test", password.repeat(4), false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            Assertions.assertEquals(ErrorCode.INVALID_PASSWORD.message, exception.message)
        }
    }

    @Test
    @DisplayName("ユーザー作成API 不正なファーストネームの場合は400エラー")
    fun testUserCreateAPI_FailedByInvalidFirstName() {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test".repeat(65), "test", "Ab-cd".repeat(4), false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            Assertions.assertEquals(ErrorCode.INVALID_FIRST_NAME.message, exception.message)
        }
    }

    @Test
    @DisplayName("ユーザー作成API 不正なラストネームの場合は400エラー")
    fun testUserCreateAPI_FailedByInvalidLastName() {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test", "test".repeat(65), "Ab-cd".repeat(4), false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            Assertions.assertEquals(ErrorCode.INVALID_LAST_NAME.message, exception.message)
        }
    }

    @Test
    @DisplayName("ユーザー作成API 管理者でない場合は403エラー")
    fun testUserCreateAPI_FailedByUserHasNoPermission() {
        val cookie = login()
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test", "test", "Test-1234", false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.status)
        }
    }

    @Test
    @DisplayName("ユーザー作成API メールアドレスが既に使用されている場合は408エラー")
    fun testUserCreateAPI_FailedByDuplicatedEmail() {
        val cookie = loginAdmin()
        val userUpsertRequest = UserUpsertRequest(user.email, "test", "test", "Test-1234", false)
        val request = HttpRequest.POST(basePath, userUpsertRequest) //
            .cookie(cookie)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.CONFLICT, exception.status)
            Assertions.assertEquals(ErrorCode.EMAIL_IS_USED.message, exception.message)
        }
    }

    @Test
    @DisplayName("ユーザー作成API ユーザーがログインしていな場合は401エラー")
    fun testUserCreateAPI_FailedByUserNotLogin() {
        val userUpsertRequest = UserUpsertRequest("test@rits.cc", "test", "test", "Test-1234", false)
        val request = HttpRequest.POST(basePath, userUpsertRequest)
        try {
            client.toBlocking().exchange(request, Argument.of(MutableHttpResponse::class.java))
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.status)
        }
    }

}
