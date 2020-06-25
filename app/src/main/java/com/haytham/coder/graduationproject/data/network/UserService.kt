package com.haytham.coder.graduationproject.data.network

import com.haytham.coder.graduationproject.utils.AuthResponse

interface UserService {
    suspend fun loginUser(username:String, password:String): AuthResponse
    suspend fun getCurrentUser(): AuthResponse
    suspend fun logout()
}