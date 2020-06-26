package com.haytham.coder.graduationproject.data.network

import com.haytham.coder.graduationproject.utils.AuthResponse

interface UserService {
    suspend fun loginUser(email:String, password:String): AuthResponse
    suspend fun signUpUser(username:String, email: String, password:String): AuthResponse
    suspend fun getCurrentUser(): AuthResponse
    suspend fun logout()
}