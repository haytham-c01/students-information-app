package com.haytham.coder.graduationproject.data.model

import com.google.firebase.firestore.DocumentId

data class NetworkBranch(
    @DocumentId
    val branchId: String? = "",
    val branch: String? = "",
    val department: String? = ""
)