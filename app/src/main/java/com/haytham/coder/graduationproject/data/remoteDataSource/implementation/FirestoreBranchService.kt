package com.haytham.coder.graduationproject.data.remoteDataSource.implementation

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IBranchService
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreBranchService @Inject constructor() : IBranchService {
    private val mBranchCol = Firebase.firestore.collection(BRANCH_COL)

    companion object {
        private const val TAG = "FirestoreBranchService"
        const val BRANCH_COL = "Branches"

    }

    override suspend fun getBranch(branchId: String): ApiResponse<NetworkBranch> {
        return try {
            val networkBranch =
                mBranchCol.document(branchId).get().await().toObject(NetworkBranch::class.java)

            Log.d(TAG, networkBranch.toString())
            if (networkBranch != null) {
                ApiSuccessResponse(networkBranch)
            } else {
                ApiEmptyResponse
            }
        } catch (e: Exception) {
            ApiErrorResponse(errorMessage = e.message ?: "Unknown error: $e")
        }

    }

    override suspend fun getBranches(): ApiResponse<List<NetworkBranch>> {
        return try {
            val branches =
                mBranchCol.get().await().documents.map { it.toObject(NetworkBranch::class.java) }
                    .requireNoNulls()
            Log.d(TAG, branches.toString())

            if (branches.isNullOrEmpty()) {
                ApiEmptyResponse
            } else {
                ApiSuccessResponse(branches)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            ApiErrorResponse(e.message.toString())
        }
    }


}