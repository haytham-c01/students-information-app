package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.ISignUpUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val IAuthRepository: IAuthRepository): ISignUpUseCase {
    override suspend fun invoke(username:String, email:String, password:String): AuthResponse {
       return IAuthRepository.signUpUser(username, email, password)
    }
}