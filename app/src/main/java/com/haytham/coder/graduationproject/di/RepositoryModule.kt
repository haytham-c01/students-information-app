package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.data.network.UserService
import com.haytham.coder.graduationproject.data.network.UserServiceImp
import com.haytham.coder.graduationproject.data.repository.UserRepositoryImp
import com.haytham.coder.graduationproject.domain.repository.UserRepository
import com.haytham.coder.graduationproject.domain.usecase.contract.LoginUseCase
import com.haytham.coder.graduationproject.domain.usecase.implementation.LoginUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {
    @Binds
    fun bindUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository

}