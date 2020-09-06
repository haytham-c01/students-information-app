package com.haytham.coder.graduationproject.data.remoteDataSource.contract

import android.graphics.Bitmap
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IStudentService {
    fun getBranchStudents(branchId:String): Flow<ApiResponse<List<NetworkStudent>>>
    suspend fun addStudent(networkStudent: NetworkStudent, studentImage: Bitmap): ApiResponse<Unit>
    suspend fun deleteStudent(studentId: String): ApiResponse<Unit>
}