package com.haytham.coder.graduationproject.data.remoteDataSource.implementation

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
        private const val TEACHER_EMAIL_FIELD = "email"
        private const val USER_NOT_REGISTERED_ERROR= "User is not registered in the Control Panel"
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser == null) {
                 AuthError("Unknown Error")
            }else{
                val user= getUser(email)
                if (user == null) {
                    AuthError(USER_NOT_REGISTERED_ERROR)
                } else {
                    Authenticated(user)
                }
            }

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
            val user= getUser(email)

            if (user == null) {
                AuthError(USER_NOT_REGISTERED_ERROR)
            } else {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                firebaseUser?.apply {
                    updateProfile(
                        userProfileChangeRequest {
                            displayName = username
                        }
                    ).await()
                }

                val updatedUser = user.copy(username = username)
                mTeacherCol.document(updatedUser.userId).update(TEACHER_USERNAME_FIELD, updatedUser.username)
                Authenticated(updatedUser)
            }

        } catch (e: Exception) {
            AuthError(e.message ?: UNKNOWN_ERROR)
        }
    }

    private suspend fun getUser(email: String): UserModel? {
        return mTeacherCol.whereEqualTo(TEACHER_EMAIL_FIELD,email).get().await().documents.firstOrNull()?.toObject(UserModel::class.java)
    }

    override suspend fun getCurrentUser(): AuthResponse {
        val firebaseUser = firebaseAuth.currentUser ?: return AuthError("Unauthenticated")
        val user=  getUser(firebaseUser.email.toString())
        return if(user== null){
            AuthError(USER_NOT_REGISTERED_ERROR)

        }else{
            Authenticated(user)
        }
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