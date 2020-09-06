package com.haytham.coder.graduationproject.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentModel(
    val studentId:String,
    val studentName:String,
    val studentImageUrl:String,
    val age: Int,
    val contact: ContactModel,
    val schoolInfo:SchoolInfoModel
): Parcelable



