package com.haytham.coder.graduationproject.data.repository

import com.haytham.coder.graduationproject.data.network.UserService
import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userService: UserService)
    : UserRepository {


    override suspend fun getCurrentUser(): AuthResponse {
        return userService.getCurrentUser()
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return  userService.loginUser(email, password)
    }

    override suspend fun logout() {
        userService.logout()
    }

}