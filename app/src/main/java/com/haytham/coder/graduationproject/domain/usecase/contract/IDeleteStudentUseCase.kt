package com.haytham.coder.graduationproject.domain.usecase.contract

import android.graphics.Bitmap
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthResponse

interface IDeleteStudentUseCase {
    suspend operator fun invoke(studentId:String): ApiResponse<Unit>
}