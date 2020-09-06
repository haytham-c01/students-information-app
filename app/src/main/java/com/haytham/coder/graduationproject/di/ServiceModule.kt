package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IAuthService
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IBranchService
import com.haytham.coder.graduationproject.data.remoteDataSource.contract.IStudentService
import com.haytham.coder.graduationproject.data.remoteDataSource.implementation.FirestoreAuthService
import com.haytham.coder.graduationproject.data.remoteDataSource.implementation.FirestoreBranchService
import com.haytham.coder.graduationproject.data.remoteDataSource.implementation.FirestoreStudentService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface ServiceModule {

    @Binds
    fun bindAuthService(userService: FirestoreAuthService): IAuthService

    @Binds
    fun bindUserService(studentService: FirestoreStudentService): IStudentService


    @Binds
    fun bindBranchService(branchService: FirestoreBranchService): IBranchService
}