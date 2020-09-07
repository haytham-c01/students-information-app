package com.haytham.coder.graduationproject.domain.usecase.contract

import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IGetBranchStudentsUseCase {
    suspend operator fun invoke(): Flow<ApiResponse<List<StudentModel>>>
}