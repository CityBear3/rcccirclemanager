package dev.postnotifier

import io.micronaut.openapi.annotation.OpenAPIInclude
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.tags.Tag

@OpenAPIDefinition(
    info = Info(
        title = "rccmanagementsystem",
        version = "0.0"
    )
)
@OpenAPIInclude(
    classes = [io.micronaut.security.endpoints.LoginController::class],
    tags = [Tag(name = "Security", description = "認証")],
    uri = "/api/login"
)
@OpenAPIInclude(
    classes = [io.micronaut.security.endpoints.LogoutController::class],
    tags = [Tag(name = "Security", description = "認証")],
    uri = "/api/logout"
)
object Api {}

fun main(args: Array<String>) {
    run(*args)
}

