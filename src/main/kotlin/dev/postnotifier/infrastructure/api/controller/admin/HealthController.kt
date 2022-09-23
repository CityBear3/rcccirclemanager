package dev.postnotifier.infrastructure.api.controller.admin

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Parameter

@Controller("/api/health")
@Secured(SecurityRule.IS_ANONYMOUS)
class HealthController {

    @Get
    @Status(HttpStatus.OK)
    fun check(@Parameter(hidden = true) request: HttpRequest<*>) {
        println(request.headers.get("User-Agent"))
    }

}
