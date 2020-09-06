package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.domain.usecase.contract.*
import com.haytham.coder.graduationproject.domain.usecase.implementation.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {

    @Binds
    fun bindLoginUseCase(loginUseCase: LoginUseCase): ILoginUseCase

    @Binds
    fun bindSignUpCase(signUpUseCase: SignUpUseCase): ISignUpUseCase

    @Binds
    fun bindGetUserUseCase(getUserUseCase: GetUserUseCase): IGetUserUseCase

    @Binds
    fun bindLogoutUseCase(logoutUseCase: LogoutUseCase): ILogoutUseCase

    @Binds
    fun bindGetBranchStudentsUseCase(getBranchStudentsUseCase: GetBranchStudentsUseCase): IGetBranchStudentsUseCase

    @Binds
    fun bindSearchStudentsUseCase(searchStudentUseCase: SearchStudentsUseCase): ISearchStudentsUseCase

    @Binds
    fun bindAddStudentUseCase(addStudentUseCase: AddStudentUseCase): IAddStudentUseCase

    @Binds
    fun bindGetBranchesUseCase(getBranchesUseCase: GetBranchesUseCase): IGetBranchesUseCase

    @Binds
    fun bindDeleteStudentUseCase(deleteStudentUseCase: DeleteStudentUseCase): IDeleteStudentUseCase

    @Binds
    fun bindSetFilterUseCase(updateFilterUseCase: UpdateFilterUseCase): IUpdateFilterUseCase

}