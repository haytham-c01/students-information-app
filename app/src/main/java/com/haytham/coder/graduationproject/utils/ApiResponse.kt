package com.haytham.coder.graduationproject.utils

import com.orhanobut.logger.Logger

sealed class ApiResponse<out T> {
    companion object {
        fun Throwable.toApiResponse(): ApiErrorResponse {
            Logger.e(message.toString())
            return ApiErrorResponse(
                message ?: "unknown error"
            )
        }

    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
object ApiEmptyResponse : ApiResponse<Nothing>()
data class ApiSuccessResponse<out T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse(val errorMessage: String) : ApiResponse<Nothing>()
