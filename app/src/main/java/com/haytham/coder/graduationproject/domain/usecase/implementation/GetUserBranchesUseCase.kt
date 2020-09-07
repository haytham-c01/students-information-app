package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.repository.IBranchRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserBranchesUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.Authenticated
import javax.inject.Inject

class GetUserBranchesUseCase @Inject constructor(
    private val branchRepository: IBranchRepository,
    private val getUserUseCase: IGetUserUseCase
) :
    IGetUserBranchesUseCase {
    override suspend operator fun invoke(): ApiResponse<List<BranchModel>> {
        return when(val authRes =getUserUseCase()){
            is Authenticated -> {
                branchRepository.getBranches(authRes.userModel.branch)
            }
            is AuthError -> {
                ApiErrorResponse(authRes.errorMessage)
            }
        }
    }
}