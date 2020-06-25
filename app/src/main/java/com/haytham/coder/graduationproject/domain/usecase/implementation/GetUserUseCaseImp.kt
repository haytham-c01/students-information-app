package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.GetUserUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse

class GetUserUseCaseImp(private val userRepository: UserRepository) :
    GetUserUseCase {
    override suspend fun invoke(): AuthResponse {
       return userRepository.getCurrentUser()
    }

}