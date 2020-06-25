package com.haytham.coder.graduationproject.utils

import com.haytham.coder.graduationproject.domain.model.UserModel

sealed class AuthResponse
/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */

data class Authenticated(val userModel: UserModel) : AuthResponse()
data class AuthError(val errorMessage: String) : AuthResponse()
