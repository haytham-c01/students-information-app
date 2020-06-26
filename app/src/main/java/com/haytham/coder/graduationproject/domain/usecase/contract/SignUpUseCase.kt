package com.haytham.coder.graduationproject.domain.usecase.contract

import com.haytham.coder.graduationproject.utils.AuthResponse

interface SignUpUseCase {
    suspend operator fun invoke(username:String, email:String, password:String): AuthResponse
}