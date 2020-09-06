package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.google.firebase.auth.AuthResult
import com.haytham.coder.graduationproject.data.repository.BranchRepository
import com.haytham.coder.graduationproject.data.repository.StudentRepository
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.repository.IBranchRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchesUseCase
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import javax.inject.Inject

class GetBranchesUseCase @Inject constructor(private val branchRepository: IBranchRepository): IGetBranchesUseCase {
    override suspend operator fun invoke(): ApiResponse<List<BranchModel>>{
        return branchRepository.getBranches();
    }
}