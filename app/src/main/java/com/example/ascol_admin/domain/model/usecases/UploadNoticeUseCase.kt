package com.example.ascol_admin.domain.model.usecases

import android.graphics.Bitmap
import com.example.ascol_admin.data.repository.NoticeRepository
import com.example.ascol_admin.domain.model.Notice
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class UploadNoticeUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(title: String, bitmap: Bitmap) {
        val compressedBitmap = compressBitmap(bitmap)
        val noticeId = UUID.randomUUID().toString()
        val imageUrl = noticeRepository.uploadImage(compressedBitmap, noticeId)

        val notice = Notice(
            title = title,
            date = getCurrentDate(),
            imageURL = imageUrl,
            time = getCurrentTime(),
            key = noticeId
        )

        noticeRepository.uploadNotice(notice)
    }

    private fun compressBitmap(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = Date()
        return timeFormat.format(date)
    }
}