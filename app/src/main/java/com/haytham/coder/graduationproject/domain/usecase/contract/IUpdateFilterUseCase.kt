package com.haytham.coder.graduationproject.domain.usecase.contract

import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IUpdateFilterUseCase {
    operator fun invoke(filter:StudentFilter): Unit
}