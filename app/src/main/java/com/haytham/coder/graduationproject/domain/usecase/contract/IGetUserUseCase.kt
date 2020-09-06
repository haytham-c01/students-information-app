package com.haytham.coder.graduationproject.domain.usecase.contract

import com.haytham.coder.graduationproject.utils.AuthResponse

interface IGetUserUseCase {
    suspend operator fun invoke(): AuthResponse
}