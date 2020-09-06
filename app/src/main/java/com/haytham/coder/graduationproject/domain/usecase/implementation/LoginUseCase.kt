package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.ILoginUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val IAuthRepository: IAuthRepository)
    : ILoginUseCase {
    override suspend fun invoke(email: String, password: String): AuthResponse {
        return IAuthRepository.loginUser(email, password)
    }
}