package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.ILogoutUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IUpdateFilterUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val IAuthRepository: IAuthRepository,
private val updateFilterUseCase: IUpdateFilterUseCase
):
    ILogoutUseCase {

    override suspend fun invoke() {
        updateFilterUseCase(StudentFilter.empty)
        IAuthRepository.logout()
    }

}