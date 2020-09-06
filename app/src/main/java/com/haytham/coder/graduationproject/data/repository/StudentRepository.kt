package com.haytham.coder.graduationproject.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.haytham.coder.graduationproject.data.mapper.IListMapper
import com.haytham.coder.graduationproject.data.mapper.ListMapper
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.data.model.NetworkStudentWithBranch
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IBranchService
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IStudentService
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val studentService: IStudentService,
    private val branchService: IBranchService,
    private val studentsMapper: IListMapper<NetworkStudentWithBranch, StudentModel>,
) : IStudentRepository {
    companion object {
        private const val TAG = "StudentRepository"
    }

    private var mCachedStudents: Flow<ApiResponse<List<StudentModel>>> = flow { }
    private var lastBranchId = ""
    private var filter: StudentFilter? = null

    override suspend fun getBranchStudents(branchId: String): Flow<ApiResponse<List<StudentModel>>> {
        if (branchId == lastBranchId) return getFilteredStudents()

        val networkBranch = branchService.getBranch(branchId)
        mCachedStudents = studentService.getBranchStudents(branchId).map {
            Log.d(TAG, it.toString())
            Log.d(TAG, networkBranch.toString())
            when (it) {
                is ApiSuccessResponse -> {
                    when (networkBranch) {
                        is ApiSuccessResponse -> {
                            val networkStudentsWithBranch = it.body.map { networkStudent ->
                                NetworkStudentWithBranch(networkStudent, networkBranch.body)
                            }
                            ApiSuccessResponse(studentsMapper.map(networkStudentsWithBranch))
                        }
                        ApiEmptyResponse -> ApiEmptyResponse
                        is ApiErrorResponse -> networkBranch
                    }
                }
                ApiEmptyResponse -> ApiEmptyResponse
                is ApiErrorResponse -> it
            }
        }

        return getFilteredStudents()
    }

    override suspend fun addStudent(networkStudent: NetworkStudent, studentImage: Bitmap): ApiResponse<Unit> {
        return studentService.addStudent(networkStudent, studentImage)
    }

    override suspend fun deleteStudent(studentId: String): ApiResponse<Unit> {
        return studentService.deleteStudent(studentId)
    }

    override fun search(keyword: String): Flow<List<StudentModel>> {
        return getFilteredStudents().map {
            if(keyword.isBlank())  listOf<StudentModel>()
            else
            when (it) {
                is ApiSuccessResponse -> it.body.filter { studentModel ->

                    val res= studentModel.run {
                        studentName.contains(keyword)
                                || contact.email.contains(keyword)
                                || contact.phone.contains(keyword)
                    }

                    Log.d(TAG, "keyword: $keyword")
                    Log.d(TAG, "${studentModel.studentName}- $res")
                    res
                }
                is ApiErrorResponse -> listOf()
                is ApiEmptyResponse -> listOf()
            }
        }
    }

    override fun setFilter(studentFilter: StudentFilter) {
        filter= studentFilter
    }

    private fun getFilteredStudents(): Flow<ApiResponse<List<StudentModel>>> {
        if (filter == null || filter == StudentFilter.empty) return mCachedStudents

        return mCachedStudents.map {
            if (it is ApiSuccessResponse) {
                val filteredList = filter?.filterList(it.body)

                if (!filteredList.isNullOrEmpty()) {
                    ApiSuccessResponse(filteredList)
                } else {
                    ApiEmptyResponse
                }
            } else {
                it
            }
        }
    }

}