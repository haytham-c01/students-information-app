package com.haytham.coder.graduationproject.data.mapper

import com.google.firebase.Timestamp
import com.haytham.coder.graduationproject.data.model.NetworkStudentWithBranch
import com.haytham.coder.graduationproject.domain.model.BranchModel
import com.haytham.coder.graduationproject.domain.model.ContactModel
import com.haytham.coder.graduationproject.domain.model.SchoolInfoModel
import com.haytham.coder.graduationproject.domain.model.StudentModel
import org.threeten.bp.*
import javax.inject.Inject

class StudentModelMapper @Inject constructor() : Mapper<NetworkStudentWithBranch, StudentModel>{
    override fun map(input: NetworkStudentWithBranch): StudentModel {
        val networkStudent= input.networkStudent
        val networkBranch= input.networkBranch


        val branchModel= BranchModel(
            branchId = networkBranch.branchId?: "",
            branchName = networkBranch.branch?: "",
            departmentName = networkBranch.department?: "",
        )

        val contactModel= ContactModel(
            cityId = networkStudent.cityId?.toInt()?:0,
            email= networkStudent.email?:"",
            phone= networkStudent.phone?:"",
            inCollegeResidence = networkStudent.inCollegeResidence?:false,
        )

        val schoolInfoModel= SchoolInfoModel(
            branch= branchModel,
            degree= networkStudent.degree?:"",
            stage = networkStudent.stage?:0,
            isEveningCollege = networkStudent.eveningCollege?:false,
            graduationYear = networkStudent.graduationYear?:0,
        )

        return StudentModel(
            studentId = networkStudent.studentId?:"",
            studentName = networkStudent.studentName?:"",
            studentImageUrl = networkStudent.studentImage?:"",
            age = networkStudent.birthday?.toLocalDateTime()?.getAge()?: LocalDateTime.now().getAge(),
            contact = contactModel,
            schoolInfo = schoolInfoModel,
        )
    }

    private fun LocalDateTime.getAge() :Int{
         return LocalDateTime.now().year - this.year
    }

    private fun Timestamp.toLocalDateTime() :LocalDateTime{
        val timeInstant: Instant =
            Instant.ofEpochSecond( this.seconds)

        return  LocalDateTime.ofInstant(timeInstant, ZoneId.systemDefault());
    }
}