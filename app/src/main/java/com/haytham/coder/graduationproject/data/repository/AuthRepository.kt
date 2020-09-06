package com.haytham.coder.graduationproject.data.repository

import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IAuthService
import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.utils.AuthResponse
import com.haytham.coder.graduationproject.utils.Authenticated
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: IAuthService
) : IAuthRepository {
    private var mCachedAuthRes: AuthResponse?= null

    override suspend fun getCurrentUser(): AuthResponse {
        return if(mCachedAuthRes == null){
            authService.getCurrentUser().apply { cacheIfSuccess() }
        }else{
            mCachedAuthRes!!
        }
    }

    private fun AuthResponse.cacheIfSuccess(){
          if(this is Authenticated){
              mCachedAuthRes= this
          }
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return  authService.loginUser(email, password).apply { cacheIfSuccess() }
    }

    override suspend fun signUpUser(
        username: String,
        email: String,
        password: String
    ): AuthResponse {
        return authService.signUpUser(username, email, password).apply { cacheIfSuccess() }
    }

    override suspend fun logout() {
        mCachedAuthRes= null
        authService.logout()
    }

}