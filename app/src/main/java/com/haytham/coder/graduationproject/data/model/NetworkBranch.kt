package com.haytham.coder.graduationproject.data.model

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

data class NetworkBranch (
    @DocumentId
    val branchId:String?="",
    val branch:String?="",
    val department:String?=""
)