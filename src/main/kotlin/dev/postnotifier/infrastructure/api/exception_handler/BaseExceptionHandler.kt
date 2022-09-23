package dev.postnotifier.infrastructure.api.exception_handler

import dev.postnotifier.infrastructure.api.response.ErrorResponse
import dev.postnotifier.exception.BaseException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

/**
 * BaseExceptionハンドラー
 */
@Produces
@Singleton
@Requires(classes = [BaseException::class, ExceptionHandler::class])
class BaseExceptionHandler : ExceptionHandler<BaseException, HttpResponse<ErrorResponse>> {
    /**
     * BaseExceptionのハンドラー
     */
    override fun handle(request: HttpRequest<*>, exception: BaseException): HttpResponse<ErrorResponse> {
        val errorCode = exception.errorCode
        return HttpResponse.status<ErrorResponse?>(exception.status) //
            .body(ErrorResponse(errorCode.code, errorCode.message))
    }

}
