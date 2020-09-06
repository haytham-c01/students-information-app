package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IDeleteStudentUseCase
import com.haytham.coder.graduationproject.utils.ApiResponse
import javax.inject.Inject

class DeleteStudentUseCase @Inject constructor(private val studentRepository: IStudentRepository) :
    IDeleteStudentUseCase {
    override suspend operator fun invoke(studentId: String): ApiResponse<Unit> {
        return studentRepository.deleteStudent(studentId)
    }
}