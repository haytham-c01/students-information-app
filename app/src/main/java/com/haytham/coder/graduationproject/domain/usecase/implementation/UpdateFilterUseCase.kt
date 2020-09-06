package com.haytham.coder.graduationproject.domain.usecase.implementation

import com.haytham.coder.graduationproject.data.repository.StudentRepository
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IUpdateFilterUseCase
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFilterUseCase @Inject constructor(private val studentRepository: IStudentRepository) : IUpdateFilterUseCase{
    override operator fun invoke(filter: StudentFilter){
        studentRepository.setFilter(filter)
    }
}