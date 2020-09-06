package com.haytham.coder.graduationproject.data.remoteDataSource.contract

import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.utils.ApiResponse

interface IBranchService {
    suspend fun getBranch(branchId:String): ApiResponse<NetworkBranch>
    suspend fun getBranches(): ApiResponse<List<NetworkBranch>>
}