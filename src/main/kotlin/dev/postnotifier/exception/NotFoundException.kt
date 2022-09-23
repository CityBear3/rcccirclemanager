package dev.postnotifier.exception

import io.micronaut.http.HttpStatus

class NotFoundException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.NOT_FOUND)
