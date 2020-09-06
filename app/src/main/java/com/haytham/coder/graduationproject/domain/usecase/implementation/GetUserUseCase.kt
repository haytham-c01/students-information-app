package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val IAuthRepository: IAuthRepository) :
    IGetUserUseCase {
    override suspend fun invoke(): AuthResponse {
       return IAuthRepository.getCurrentUser()
    }

}