package com.haytham.coder.graduationproject.data.remoteDataSource.contract

import com.haytham.coder.graduationproject.utils.AuthResponse

interface IAuthService {
    suspend fun loginUser(email:String, password:String): AuthResponse
    suspend fun signUpUser(username:String, email: String, password:String): AuthResponse
    suspend fun getCurrentUser(): AuthResponse
    suspend fun logout()
}