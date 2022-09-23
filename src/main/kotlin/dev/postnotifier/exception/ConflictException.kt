package dev.postnotifier.exception

import io.micronaut.http.HttpStatus

class ConflictException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.CONFLICT)
