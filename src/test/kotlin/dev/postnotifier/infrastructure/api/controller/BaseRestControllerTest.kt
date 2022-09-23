package dev.postnotifier.infrastructure.api.controller

import dev.postnotifier.domain.model.UserModel
import dev.postnotifier.util.AuthUtil
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.cookie.Cookie
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

@MicronautTest
open class BaseRestControllerTest {

    @Inject
    lateinit var authUtil: AuthUtil

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var admin: UserModel

    lateinit var user: UserModel

    val connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:55432/circle-manager",
        "circle-manager",
        "circle-manager"
    )

    @BeforeEach
    fun setup() {
        admin = UserModel(
            UUID.randomUUID(),
            "admin@example.com",
            "John",
            "Due",
            authUtil.encode("0000"),
            true
        )
        user = UserModel(
            UUID.randomUUID(),
            "user@example.com",
            "Jane",
            "Due",
            authUtil.encode("0000"),
            false
        )
        connection.prepareStatement(
            """
            INSERT INTO users(id, email, first_name, last_name, password, is_admin) 
                VALUES ('${admin.id}', '${admin.email}', '${admin.firstName}', '${admin.lastName}', '${admin.password}', '${admin.isAdmin}'),
                ('${user.id}', '${user.email}', '${user.firstName}', '${user.lastName}', '${user.password}', '${user.isAdmin}')
        """.trimIndent()
        ).execute()
    }

    @AfterEach
    fun cleanup() {
        connection.prepareStatement("TRUNCATE users CASCADE ").execute()
    }

    fun loginAdmin(): Cookie {
        val url = "/api/login"
        val credentials = UsernamePasswordCredentials(admin.email, "0000")
        val response = client.toBlocking() //
            .exchange(HttpRequest.POST(url, credentials), Argument.of(MutableHttpResponse::class.java))
        return response.cookies.get("SESSION")
    }

    fun login(): Cookie {
        val url = "/api/login"
        val credentials = UsernamePasswordCredentials(user.email, "0000")
        val response = client.toBlocking() //
            .exchange(HttpRequest.POST(url, credentials), Argument.of(MutableHttpResponse::class.java))
        return response.cookies.get("SESSION")
    }

}
