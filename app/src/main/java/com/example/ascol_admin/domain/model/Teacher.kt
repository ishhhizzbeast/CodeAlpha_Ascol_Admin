package com.example.ascol_admin.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class Teacher (
    val key: String = "",
    val department: String = "",
    val email: String = "",
    val image: String = "",
    val name: String = "",
    val post: String = ""
){
    // No-argument constructor required for Firebase
    constructor() : this("", "", "", "", "", "")
}
