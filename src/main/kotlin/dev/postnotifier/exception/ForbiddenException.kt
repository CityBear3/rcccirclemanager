package dev.postnotifier.exception

import io.micronaut.http.HttpStatus

class ForbiddenException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.FORBIDDEN)
