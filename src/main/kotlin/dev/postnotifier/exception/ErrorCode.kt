package dev.postnotifier.exception

import io.micronaut.http.HttpStatus

enum class ErrorCode(val code: Int, val message: String) {

    /**
     * 400
     */
    INVALID_EMAIL(HttpStatus.BAD_REQUEST.code, "メールアドレスの形式が不正、または255文字を超えています。"),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.code, "パスワードは8文字以上24文字以下、大文字のアルファベットと「 . / ? - 」の記号から最低一つを含む必要があります。"),

    INVALID_FIRST_NAME(HttpStatus.BAD_REQUEST.code, "ファーストネームの長さが255文字を超えています。"),

    INVALID_LAST_NAME(HttpStatus.BAD_REQUEST.code, "ラストネームの長さが255文字を超えています。"),

    /**
     * 401
     */
    INVALID_CERTIFICATIONS(HttpStatus.UNAUTHORIZED.code, "認証情報が間違っています。"),

    /**
     * 403
     */
    USER_HAS_NO_PERMISSION(HttpStatus.FORBIDDEN.code, "この操作を行うための権限がありません。"),

    /**
     * 404
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.code, "ユーザーが見つかりません。"),

    /**
     * 408
     */
    EMAIL_IS_USED(HttpStatus.CONFLICT.code, "そのメールアドレスは既に使用されています。"),

    /**
     * 500
     */
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.code, "不明なエラーが発生しました。問題が解決しない場合は管理者にお問い合わせください。"),

    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.code, "予期せぬエラーが発生しました。今一度操作を見直してください。")

}
