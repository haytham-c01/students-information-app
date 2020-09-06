package com.haytham.coder.graduationproject.domain.usecase.implementation

import android.graphics.Bitmap
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.data.repository.StudentRepository
import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IAddStudentUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.ILoginUseCase
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class AddStudentUseCase @Inject constructor(
    private val studentRepository: IStudentRepository)
    : IAddStudentUseCase {
    override suspend fun invoke(networkStudent: NetworkStudent, studentImage:Bitmap): ApiResponse<Unit> {
        return studentRepository.addStudent(networkStudent, studentImage)
    }

}