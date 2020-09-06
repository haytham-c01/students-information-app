package com.haytham.coder.graduationproject.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import org.threeten.bp.LocalDateTime

data class NetworkStudent (
    @DocumentId
    val studentId:String?="",
    val studentName:String?="",
    val studentImage:String?="",
    val birthday: Timestamp?= null,
    val graduationYear: Int?= null,
    val cityId:String?= "",
    val email:String?="",
    val phone:String?="",
    val inCollegeResidence:Boolean?=null,
    val branch:DocumentReference?=null,
    val degree:String?="",
    val stage:Int?= null,
    val eveningCollege:Boolean?= null
)


