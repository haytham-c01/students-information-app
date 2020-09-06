package com.haytham.coder.graduationproject.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BranchModel (
    val branchId:String,
    val branchName:String,
    val departmentName:String,
): Parcelable