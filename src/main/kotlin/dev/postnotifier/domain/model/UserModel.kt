package dev.postnotifier.domain.model

import java.util.*

/**
 * ユーザーモデル
 */
class UserModel(
    /**
     * ユーザーID
     */
    val id: UUID? = null,

    /**
     * メールアドレス
     */
    val email: String,

    /**
     * ファーストネーム
     */
    val firstName: String,

    /**
     * ラストネーム
     */
    val lastName: String,

    /**
     * パスワード
     */
    val password: String,

    /**
     *   管理者フラグ
     */
    val isAdmin: Boolean = false

) {

    fun checkPassword(): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*[.?/-])[a-zA-Z0-9.?/-]{8,24}\$")
        return password.matches(regex)
    }

    fun checkEmail(): Boolean {
        val regex = Regex("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,255}\$")
        return email.matches(regex)
    }

}
