package com.haytham.coder.graduationproject.domain.model

import com.google.firebase.firestore.DocumentId

data class UserModel (
    @DocumentId
    val userId:String= "",
    val username:String= "",
    val email:String= "",
    val canWrite:Boolean= false,
    val branch:String= "",
)