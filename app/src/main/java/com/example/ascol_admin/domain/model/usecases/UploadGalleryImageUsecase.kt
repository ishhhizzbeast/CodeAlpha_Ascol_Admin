package com.example.ascol_admin.domain.model.usecases

import android.graphics.Bitmap
import com.example.ascol_admin.data.galleryRepository.GalleryRepository
import javax.inject.Inject

class UploadGalleryImageUsecase @Inject constructor(
    val repository: GalleryRepository
) {
    suspend operator fun invoke(category:String,image:Bitmap):String{
        return repository.uploadGalleryImage(category,image)
    }
}