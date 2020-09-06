package com.haytham.coder.graduationproject.domain.usecase.contract

import com.google.firebase.auth.AuthResult
import com.haytham.coder.graduationproject.utils.AuthResponse

interface ILoginUseCase {
    suspend operator fun invoke(email:String, password:String): AuthResponse
}