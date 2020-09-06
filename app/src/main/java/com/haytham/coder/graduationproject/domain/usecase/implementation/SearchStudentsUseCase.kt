package com.haytham.coder.graduationproject.domain.usecase.implementation

import android.util.Log
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchStudentsUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.ISearchStudentsUseCase
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchStudentsUseCase @Inject constructor(private val studentsRepo: IStudentRepository) :
    ISearchStudentsUseCase {
    companion object{
        private const val TAG= "SearchStudentsUseCase"
    }
    override fun invoke(keyword: String): Flow<List<StudentModel>> {
        Log.d("studentsRepo", studentsRepo.toString())
        return studentsRepo.search(keyword)
    }

}