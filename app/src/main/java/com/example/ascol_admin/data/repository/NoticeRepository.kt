package com.example.ascol_admin.data.repository

import com.example.ascol_admin.domain.model.Notice

interface NoticeRepository {
    suspend fun uploadNotice(notice:Notice)
    suspend fun uploadImage(imageData:ByteArray,noticeId:String):String
}