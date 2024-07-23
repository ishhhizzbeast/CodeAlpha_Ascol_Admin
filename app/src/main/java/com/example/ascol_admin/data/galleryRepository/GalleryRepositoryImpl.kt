package com.example.ascol_admin.data.galleryRepository

import android.graphics.Bitmap
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
   private val firebaseStorage: FirebaseStorage,
    private  val firebaseDatabase: FirebaseDatabase
) : GalleryRepository {
    override suspend fun uploadGalleryImage(category: String, bitmap: Bitmap): String {
        val storageRef = firebaseStorage.reference.child("gallery/$category/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val imageData = baos.toByteArray()

        val uploadTask = storageRef.putBytes(imageData).await()
        val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

        val galleryRef = firebaseDatabase.getReference("gallery").child(category).push()
        val key = galleryRef.key
        val galleryData = mapOf(
            key!! to downloadUrl
        )
        galleryRef.setValue(galleryData).await()
        return key
    }
}