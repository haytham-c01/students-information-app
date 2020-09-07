package com.haytham.coder.graduationproject.domain.usecase.implementation

import android.util.Log
import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.IGetUserUseCase
import com.haytham.coder.graduationproject.utils.AuthResponse
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val IAuthRepository: IAuthRepository) :
    IGetUserUseCase {

    companion object{
        const val TAG= "GetUserUseCase"
    }
    override suspend fun invoke(): AuthResponse {
        val authRes=  IAuthRepository.getCurrentUser()
        Log.d(TAG, authRes.toString())
       return authRes
    }

}