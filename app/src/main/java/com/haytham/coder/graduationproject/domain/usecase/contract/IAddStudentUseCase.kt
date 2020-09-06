package com.haytham.coder.graduationproject.domain.usecase.contract

import android.graphics.Bitmap
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthResponse

interface IAddStudentUseCase {
    suspend operator fun invoke(networkStudent: NetworkStudent, studentImage: Bitmap): ApiResponse<Unit>
}