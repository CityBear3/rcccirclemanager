package dev.postnotifier.infrastructure.provider

import dev.postnotifier.domain.repository.UserRepository
import dev.postnotifier.exception.ErrorCode
import dev.postnotifier.exception.NotFoundException
import dev.postnotifier.exception.UnauthorizedException
import dev.postnotifier.util.AuthUtil
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword(
    private val userRepository: UserRepository,
    private val authUtil: AuthUtil
) : AuthenticationProvider {

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        val user = userRepository.findByEmail(authenticationRequest?.identity as String) //
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER)
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            if (authUtil.verify(authenticationRequest.secret as String, user.password)) {
                if (user.isAdmin) {
                    emitter.next(AuthenticationResponse.success(user.id.toString(), listOf("ROLE_ADMIN")))
                } else {
                    emitter.next(AuthenticationResponse.success(user.id.toString(), listOf("ROLE_USER")))
                }
                emitter.complete()
            } else {
                emitter.error(UnauthorizedException(ErrorCode.INVALID_CERTIFICATIONS))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
