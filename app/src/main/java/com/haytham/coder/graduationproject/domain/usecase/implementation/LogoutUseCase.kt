package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.ILogoutUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val IAuthRepository: IAuthRepository):
    ILogoutUseCase {

    override suspend fun invoke() {
        IAuthRepository.logout()
    }

}