package com.haytham.coder.graduationproject.data.repository

import android.util.Log
import com.haytham.coder.graduationproject.data.mapper.IListMapper
import com.haytham.coder.graduationproject.data.mapper.ListMapper
import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.data.model.NetworkStudentWithBranch
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IBranchService
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IStudentService
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.domain.repository.IBranchRepository
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BranchRepository @Inject constructor(
    private val branchService: IBranchService,
    private val branchMapper: IListMapper<NetworkBranch, BranchModel>,
) : IBranchRepository {
    companion object{
        private const val TAG= "BranchRepository"
    }
    override suspend fun getBranches(branchName:String): ApiResponse<List<BranchModel>> {
        val res= branchService.getBranches(branchName)
        Log.d(TAG, res.toString());
        return when(res){

            ApiEmptyResponse -> ApiEmptyResponse
            is ApiSuccessResponse -> ApiSuccessResponse(branchMapper.map(res.body))
            is ApiErrorResponse -> res
        }
    }


}