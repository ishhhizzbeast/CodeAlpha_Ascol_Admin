package com.example.ascol_admin.data.datasource.NoticeDataSource

import com.example.ascol_admin.domain.model.Notice

interface NoticeRemoteDataSource {
    suspend fun uploadImage(imageData: ByteArray,noticeId:String): String
    suspend fun uploadNotice(notice: Notice)
}
