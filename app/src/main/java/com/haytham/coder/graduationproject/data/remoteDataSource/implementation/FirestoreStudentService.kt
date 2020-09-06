package com.haytham.coder.graduationproject.data.remoteDataSource.implementation

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.haytham.coder.graduationproject.data.model.NetworkStudent
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IStudentService
import com.haytham.coder.graduationproject.utils.ApiEmptyResponse
import com.haytham.coder.graduationproject.utils.ApiErrorResponse
import com.haytham.coder.graduationproject.utils.ApiResponse
import com.haytham.coder.graduationproject.utils.ApiSuccessResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirestoreStudentService @Inject constructor() : IStudentService {
    private val mStudentsCol = Firebase.firestore.collection(STUDENT_COL)

    companion object {
        private const val STUDENT_COL = "Students"
        private const val BRANCH_COL = "Branches"
        private const val BRANCH_FIELD = "branch"
        private const val STUDENT_NAME_FIELD = "studentName"
        private const val STUDENT_PROFILE_IMAGE_FOLDER = "studentsImages"
        private const val TAG = "FirestoreStudentService"
    }

    @ExperimentalCoroutinesApi
    override fun getBranchStudents(branchId: String): Flow<ApiResponse<List<NetworkStudent>>> {
        Log.d(TAG, "getStudents")
        val branchRef = Firebase.firestore.collection(BRANCH_COL).document(branchId)
            val query =
                mStudentsCol.whereEqualTo(BRANCH_FIELD, branchRef).orderBy(STUDENT_NAME_FIELD)

            return callbackFlow {
                val listener = query.addSnapshotListener { querySnapshot, e ->
                    if (e != null) {
                        offer(ApiErrorResponse(e.message ?: "Unknown error: $e"))
                        close()
                    }

                    if (querySnapshot != null && querySnapshot.documents.isNotEmpty()) {
                        val students = mutableListOf<NetworkStudent?>()
                        querySnapshot.documents.forEach {
                            students.add(it.toObject(NetworkStudent::class.java))
                        }

                        val notNullStudents = students.filterNotNull()
                        //Log.d(TAG, notNullStudents.toString())

                        if (notNullStudents.isNotEmpty()) {
                            offer(ApiSuccessResponse(notNullStudents))
                        } else {
                            offer(ApiEmptyResponse)
                        }
                    } else {
                        offer(ApiEmptyResponse)
                    }
                }

                awaitClose { listener.remove() }
            }

    }

    override suspend fun addStudent(
        networkStudent: NetworkStudent,
        studentImage: Bitmap
    ): ApiResponse<Unit> {
        return try {
            mStudentsCol.document().apply {
                val imageUrl = uploadStudentImage(studentImage, this.id)
                set(networkStudent.copy(studentImage = imageUrl)).await()
            }

            ApiSuccessResponse(Unit)
        } catch (e: Exception) {
            ApiErrorResponse(e.message.toString())
        }
    }

    override suspend fun deleteStudent(studentId: String): ApiResponse<Unit> {
        return try {
            mStudentsCol.document(studentId).delete().await()
            Firebase.storage.reference.child("$STUDENT_PROFILE_IMAGE_FOLDER/$studentId.webp").delete().await()
            ApiSuccessResponse(Unit)
        } catch (e: Exception) {
            ApiErrorResponse(e.message.toString())
        }
    }

    private suspend fun uploadStudentImage(
        studentImage: Bitmap,
        studentId: String
    ): String {
        val baos = ByteArrayOutputStream()
        studentImage.compress(Bitmap.CompressFormat.WEBP, 50, baos)
        val ref = Firebase.storage.reference.child("$STUDENT_PROFILE_IMAGE_FOLDER/$studentId.webp")
        ref.putBytes(baos.toByteArray()).await()
        val downloadUrl = ref.downloadUrl.await().toString()

        Log.d(TAG, downloadUrl)
        return downloadUrl
    }

}