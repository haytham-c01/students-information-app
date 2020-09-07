package com.haytham.coder.graduationproject.data.remoteDataSource.contract

import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.utils.ApiResponse

interface IBranchService {
    //suspend fun getBranch(branchName:String): ApiResponse<List<NetworkBranch>>
    suspend fun getBranches(branchName:String): ApiResponse<List<NetworkBranch>>
}