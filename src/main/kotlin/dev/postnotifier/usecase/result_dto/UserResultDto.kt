package dev.postnotifier.usecase.result_dto

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class UserResultDto(
    val id: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isAdmin: Boolean
)
