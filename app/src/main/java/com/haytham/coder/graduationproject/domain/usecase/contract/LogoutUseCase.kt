package com.haytham.coder.graduationproject.domain.usecase.contract

import com.google.firebase.auth.AuthResult

interface LogoutUseCase {
    suspend operator fun invoke()
}