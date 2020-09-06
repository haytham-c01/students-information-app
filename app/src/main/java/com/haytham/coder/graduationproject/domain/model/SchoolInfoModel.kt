package com.haytham.coder.graduationproject.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
data class SchoolInfoModel(
    val branch: BranchModel,
    val degree:String,
    val stage:Int?,
    val graduationYear: Int?,
    val isEveningCollege:Boolean,
    val isGraduate:Boolean = graduationYear!= null&& graduationYear>0
): Parcelable