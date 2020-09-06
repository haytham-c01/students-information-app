package com.haytham.coder.graduationproject.domain.model

import org.threeten.bp.LocalDateTime


data class StudentFilter(
    val isStudent: Boolean?=null,
    val stage: Int?=null,
    val graduationYear: Int?=null,
    val degree: String?=null,
    val department: String?=null,
    val branch: String?=null,
    val cityId: Int?=null,
    val inCollegeResidence: Boolean?=null,
    val isEveningCollege: Boolean?=null
) {
    fun filterList(students: List<StudentModel>): List<StudentModel> {
        return students.filter { canPassFilter(it) }
    }

    private fun canPassFilter(studentModel: StudentModel) = studentModel.let {
        !(isStudent != null && isStudent == (it.schoolInfo.isGraduate)) &&
                !(stage != null && stage != it.schoolInfo.stage) &&
                !(graduationYear != null && graduationYear != it.schoolInfo.graduationYear) &&
                !(degree != null && degree.toLowerCase().trim() != it.schoolInfo.degree.toLowerCase().trim()) &&
                !(department != null && department.toLowerCase().trim()  != it.schoolInfo.branch.departmentName.toLowerCase().trim() ) &&
                !(branch != null && branch.toLowerCase().trim()  != it.schoolInfo.branch.branchName.toLowerCase().trim() ) &&
                !(cityId != null && cityId != it.contact.cityId) &&
                !(inCollegeResidence != null && inCollegeResidence != it.contact.inCollegeResidence) &&
                !(isEveningCollege != null && isEveningCollege != it.schoolInfo.isEveningCollege)

    }

    companion object{
        val empty =StudentFilter()
    }
}