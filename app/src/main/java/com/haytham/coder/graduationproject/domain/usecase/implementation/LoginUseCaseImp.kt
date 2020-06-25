package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.LoginUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class LoginUseCaseImp @Inject constructor(
    private val userRepository: UserRepository)
    : LoginUseCase {
    override suspend fun invoke(email: String, password: String): AuthResponse {
        return userRepository.loginUser(email, password)
    }
}