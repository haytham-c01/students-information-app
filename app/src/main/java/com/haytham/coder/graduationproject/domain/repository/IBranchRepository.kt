package com.haytham.coder.graduationproject.domain.repository

import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.StudentFilter
import com.haytham.coder.graduationproject.domain.model.StudentModel
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.AuthResponse
import kotlinx.coroutines.flow.Flow

interface IBranchRepository {

    suspend fun getBranches(): ApiResponse<List<BranchModel>>

}