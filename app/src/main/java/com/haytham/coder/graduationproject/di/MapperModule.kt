package com.haytham.coder.graduationproject.di

import com.haytham.coder.graduationproject.data.mapper.*
import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.data.model.NetworkStudentWithBranch
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.StudentModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MapperModule {

    companion object{
        @Provides
        fun bindStudentModelListMapper(mapper: Mapper<NetworkStudentWithBranch, StudentModel>): IListMapper<NetworkStudentWithBranch, StudentModel>
                = ListMapper(mapper)

        @Provides
        fun bindBranchModelListMapper(mapper: Mapper<NetworkBranch, BranchModel>): IListMapper<NetworkBranch, BranchModel>
                = ListMapper(mapper)
    }

    @Binds
    abstract fun bindStudentModelMapper(mapper: StudentModelMapper): Mapper<NetworkStudentWithBranch, StudentModel>


    @Binds
    abstract fun bindBranchModelMapper(mapper: BranchModelMapper): Mapper<NetworkBranch, BranchModel>
}