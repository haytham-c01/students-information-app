package com.haytham.coder.graduationproject.data.mapper

import com.google.firebase.Timestamp
import com.haytham.coder.graduationproject.data.model.NetworkBranch
import com.haytham.coder.graduationproject.data.model.NetworkStudentWithBranch
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.ContactModel
import com.haytham.coder.graduationproject.domain.model.SchoolInfoModel
import com.haytham.coder.graduationproject.domain.model.StudentModel
import org.threeten.bp.*
import javax.inject.Inject

class BranchModelMapper @Inject constructor() : Mapper<NetworkBranch, BranchModel>{
    override fun map(input: NetworkBranch): BranchModel {
        return BranchModel(
            branchId = input.branchId?: "",
            branchName = input.branch?: "",
            departmentName = input.department?:"",
        )
    }

}