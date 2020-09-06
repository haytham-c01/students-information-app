package com.haytham.coder.graduationproject.data.remoteDataSource.implementation

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IAuthService
import com.haytham.coder.graduationproject.domain.model.UserModel
import com.haytham.coder.graduationproject.utils.AuthError
import com.haytham.coder.graduationproject.utils.AuthResponse
import com.haytham.coder.graduationproject.utils.Authenticated
import com.haytham.coder.graduationproject.utils.UNKNOWN_ERROR
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirestoreAuthService @Inject constructor() : IAuthService {
    private val firebaseAuth = Firebase.auth
    private val mTeacherCol = Firebase.firestore.collection(TEACHER_COL)

    companion object {
        private const val TEACHER_COL = "Teachers"
        private const val TEACHER_USERNAME_FIELD = "username"
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            getUser(firebaseUser)
        } catch (e: Exception) {
            AuthError(e.message ?: UNKNOWN_ERROR)
        }
    }

    override suspend fun signUpUser(
        username: String,
        email: String,
        password: String
    ): AuthResponse {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            firebaseUser?.apply {
                updateProfile(
                    userProfileChangeRequest {
                        displayName = username
                    }
                ).await()
            }

            val authRes = getUser(firebaseUser)
            if (authRes is Authenticated) {
                val user = authRes.userModel.copy(username = username)
                mTeacherCol.document(user.userId).update(TEACHER_USERNAME_FIELD, user.username)
                Authenticated(user)
            } else {
                authRes
            }
        } catch (e: Exception) {
            AuthError(e.message ?: UNKNOWN_ERROR)
        }
    }

    private suspend fun getUser(firebaseUser: FirebaseUser?): AuthResponse {
        if (firebaseUser == null) {
            return AuthError("Unknown Error")
        }
        val userModel =
            mTeacherCol.document(firebaseUser.uid).get().await().toObject(UserModel::class.java)
        return if (userModel == null) {
            AuthError("User is not registered in the Control Panel")
        } else {
            Authenticated(userModel)
        }
    }

    override suspend fun getCurrentUser(): AuthResponse {
        val firebaseUser = firebaseAuth.currentUser
        return getUser(firebaseUser)
    }

//    private fun FirebaseUser?.toAuthResponse(): AuthResponse {
//        return if (this != null) {
//            val userModel = UserModel(uid, displayName, email)
//            Authenticated(userModel)
//        } else {
//            AuthError(UNKNOWN_ERROR)
//        }
//    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}