package com.haytham.coder.graduationproject.domain.usecase.implementation

import android.util.Log
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetBranchStudentsUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.Authenticated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBranchStudentsUseCase @Inject constructor(
    private val studentsRepo: IStudentRepository,
    private val getUserUseCase: IGetUserUseCase
) :
    IGetBranchStudentsUseCase {
    companion object {
        private const val TAG = "GetBranchStudentsUC"
    }

    override suspend fun invoke(): Flow<ApiResponse<List<StudentModel>>> {
        Log.d(TAG, studentsRepo.toString())
        return when(val authRes= getUserUseCase()){
            is Authenticated -> {
                Log.d(TAG, authRes.userModel.toString())
                studentsRepo.getBranchStudents(authRes.userModel.branch)
            }
            is AuthError -> {
                flow { ApiErrorResponse(authRes.errorMessage) }
            }
        }

    }

}