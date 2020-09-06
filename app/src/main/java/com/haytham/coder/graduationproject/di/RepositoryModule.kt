package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.data.repository.AuthRepository
import com.haytham.coder.graduationproject.data.repository.BranchRepository
import com.haytham.coder.graduationproject.data.repository.StudentRepository
import com.haytham.coder.graduationproject.domain.repository.IAuthRepository
import com.haytham.coder.graduationproject.domain.repository.IBranchRepository
import com.haytham.coder.graduationproject.domain.repository.IStudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    fun bindAuthRepository(userRepository: AuthRepository): IAuthRepository

    @ActivityRetainedScoped
    @Binds
    fun bindUserRepository(studentRepository: StudentRepository): IStudentRepository

    @Binds
    fun bindBranchRepository(branchRepository: BranchRepository): IBranchRepository

}