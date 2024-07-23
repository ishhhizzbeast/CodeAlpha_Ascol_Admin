package com.example.ascol_admin.data.repository

import com.example.ascol_admin.data.datasource.NoticeRemoteDataSource
import com.example.ascol_admin.domain.model.Notice
import javax.inject.Inject


class NoticeRepositoryImpl @Inject constructor(
    private val noticeRemoteDataSource: NoticeRemoteDataSource
) : NoticeRepository {
    override suspend fun uploadNotice(notice: Notice) {
        noticeRemoteDataSource.uploadNotice(notice)
    }

    override suspend fun uploadImage(imageData: ByteArray,noticeId:String): String {
        return noticeRemoteDataSource.uploadImage(imageData,noticeId)
    }

}