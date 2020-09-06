package com.haytham.coder.graduationproject.domain.usecase.contract

import com.google.firebase.auth.AuthResult
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.utils.ApiResponse

interface IGetBranchesUseCase {
    suspend operator fun invoke(): ApiResponse<List<BranchModel>>
}