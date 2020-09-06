package com.haytham.coder.graduationproject.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactModel(
    val cityId:Int,
    val email:String,
    val phone:String,
    val inCollegeResidence:Boolean
): Parcelable