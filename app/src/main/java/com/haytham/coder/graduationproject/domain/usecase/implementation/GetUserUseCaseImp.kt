package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.GetUserUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class GetUserUseCaseImp @Inject constructor(private val userRepository: UserRepository) :
    GetUserUseCase {
    override suspend fun invoke(): AuthResponse {
       return userRepository.getCurrentUser()
    }

}