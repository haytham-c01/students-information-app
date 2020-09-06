package com.haytham.coder.graduationproject.domain.usecase.implementation

import android.util.Log
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchStudentsUseCase
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBranchStudentsUseCase @Inject constructor(private val studentsRepo: IStudentRepository) :
    IGetBranchStudentsUseCase {
    companion object{
        private const val TAG= "GetBranchStudentsUC"
    }
    override suspend fun invoke(branchId: String): Flow<ApiResponse<List<StudentModel>>> {
        Log.d(TAG, studentsRepo.toString())

        return studentsRepo.getBranchStudents(branchId)

    }

}