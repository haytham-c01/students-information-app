package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.SignUpUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class SignUpUseCaseImp @Inject constructor(private val userRepository: UserRepository): SignUpUseCase {
    override suspend fun invoke(username:String, email:String, password:String): AuthResponse {
       return userRepository.signUpUser(username, email, password)
    }
}