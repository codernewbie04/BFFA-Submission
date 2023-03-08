package com.akmalmf24.githubuser.abstraction.data

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 00:59.
 * akmalmf007@gmail.com
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?,
    val cause: HttpResult = HttpResult.UNKNOWN,
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(
            status = Status.SUCCESS,
            data = data,
            message = null,
            code = null
        )

        fun <T> error(
            data: T? = null,
            message: String? = null,
            code: Int? = null,
            cause: HttpResult = HttpResult.UNKNOWN
        ): Resource<T & Any> = Resource(
            status = Status.ERROR,
            data = data,
            message = message,
            code = code,
            cause = cause
        )

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(
                status = Status.LOADING,
                data = data,
                message = null,
                code = null
            )
    }
}