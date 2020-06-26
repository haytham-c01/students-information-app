package com.haytham.coder.graduationproject.domain.repository

import com.haytham.coder.graduationproject.utils.AuthResponse

interface UserRepository {

    suspend fun getCurrentUser(): AuthResponse
    suspend fun loginUser(email:String, password:String): AuthResponse
    suspend fun signUpUser(username:String, email:String, password:String): AuthResponse

    suspend fun logout()

}