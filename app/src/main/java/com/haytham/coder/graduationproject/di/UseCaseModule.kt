package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.data.network.UserService
import com.haytham.coder.graduationproject.data.network.UserServiceImp
import com.haytham.coder.graduationproject.data.repository.UserRepositoryImp
import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.GetUserUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.LoginUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.LogoutUseCase
import com.haytham.coder.graduationproject.domain.usecase.contract.SignUpUseCase
import com.haytham.coder.graduationproject.domain.usecase.implementation.GetUserUseCaseImp
import com.haytham.coder.graduationproject.domain.usecase.implementation.LoginUseCaseImp
import com.haytham.coder.graduationproject.domain.usecase.implementation.LogoutUseCaseImp
import com.haytham.coder.graduationproject.domain.usecase.implementation.SignUpUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {

    @Binds
    fun bindLoginUseCase(loginUseCaseImp: LoginUseCaseImp): LoginUseCase

    @Binds
    fun bindSignUpCase(signUpUseCaseImp: SignUpUseCaseImp): SignUpUseCase

    @Binds
    fun bindGetUserUseCase(getUserUseCaseImp: GetUserUseCaseImp): GetUserUseCase

    @Binds
    fun bindLogoutUseCase(logoutUseCaseImp: LogoutUseCaseImp): LogoutUseCase
}