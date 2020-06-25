package com.haytham.coder.graduationproject.data.network

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.haytham.coder.graduationproject.domain.model.UserModel
import com.haytham.coder.graduationproject.utils.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class UserServiceImp @Inject constructor(): UserService{
    private val mAuth= Firebase.auth

    override suspend fun loginUser(username: String, password: String): AuthResponse {
        return try {
            val result = mAuth.signInWithEmailAndPassword(username, password).await()
            val user = result.user

            user.toAuthResponse()
        }catch (e: Exception){
            AuthError(e.message?: UNKNOWN_ERROR)
        }
    }

    override suspend fun getCurrentUser(): AuthResponse {
        val user = mAuth.currentUser
        return user.toAuthResponse()
    }

    private fun FirebaseUser?.toAuthResponse(): AuthResponse {
        return if (this != null) {
            val userModel = UserModel(uid, displayName, email)
            Authenticated(userModel)
        } else {
            AuthError(UNKNOWN_ERROR)
        }
    }

    override suspend fun logout() {
        mAuth.signOut()
    }
}