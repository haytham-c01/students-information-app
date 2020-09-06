package com.haytham.coder.graduationproject.domain.repository

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthResponse
import kotlinx.coroutines.flow.Flow

interface IStudentRepository {

    suspend fun getBranchStudents(branchId:String): Flow<ApiResponse<List<StudentModel>>>
    suspend fun addStudent(networkStudent: NetworkStudent, studentImage: Bitmap): ApiResponse<Unit>
    suspend fun deleteStudent(studentId: String): ApiResponse<Unit>
    fun search(keyword:String): Flow<List<StudentModel>>
    fun setFilter (studentFilter: StudentFilter)
}