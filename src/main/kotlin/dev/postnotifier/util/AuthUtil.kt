package dev.postnotifier.util

import jakarta.inject.Singleton
import java.security.MessageDigest

@Singleton
class AuthUtil {

    fun encode(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(password.encodeToByteArray())

        val bytes = messageDigest.digest()
        val stringBuilder = StringBuilder()

        bytes.forEach { byte ->
            val hex = String.format("%02x", byte)
            stringBuilder.append(hex)
        }
        return stringBuilder.toString()
    }

    fun verify(rawPassword: String, encodedPassword: String): Boolean {
        return encode(rawPassword) == encodedPassword
    }

}
