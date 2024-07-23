package com.example.ascol_admin.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.usecases.UploadGalleryImageUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewmodel @Inject constructor(
    private val uploadGalleryImageUsecase: UploadGalleryImageUsecase
) :ViewModel(){
    private val _uploadStatus = MutableStateFlow<String?>(null)
    var uploadStatus = _uploadStatus.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLaoding = _isLoading.asStateFlow()

    fun uploadGalleryImage(catogory:String,image: Bitmap){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                uploadGalleryImageUsecase(catogory, image)
                _uploadStatus.value = "Image uploaded successfully"
            } catch (e: Exception) {
                _uploadStatus.value = "Failed to upload image"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun resetUploadState(){
        _uploadStatus.value = null

    }
}