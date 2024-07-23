package com.example.ascol_admin.data.galleryRepository

import android.graphics.Bitmap

interface GalleryRepository {

    suspend fun uploadGalleryImage(category: String, bitmap: Bitmap): String
}