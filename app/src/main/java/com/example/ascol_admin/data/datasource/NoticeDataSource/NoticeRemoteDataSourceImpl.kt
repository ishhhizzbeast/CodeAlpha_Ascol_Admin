package com.example.ascol_admin.data.datasource.NoticeDataSource

import com.example.ascol_admin.data.datasource.NoticeDataSource.NoticeRemoteDataSource
import com.example.ascol_admin.domain.model.Notice
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseDatabase: FirebaseDatabase
) : NoticeRemoteDataSource {

    override suspend fun uploadImage(imageData: ByteArray, noticeId: String): String {
        val storageRef = firebaseStorage.reference.child("Notice/$noticeId.jpg")
        storageRef.putBytes(imageData).await()
        return storageRef.downloadUrl.await().toString()
    }

    override suspend fun uploadNotice(notice: Notice) {
        val databaseRef = firebaseDatabase.reference.child("Notices").child(notice.key)
        databaseRef.setValue(notice).await()
    }
}