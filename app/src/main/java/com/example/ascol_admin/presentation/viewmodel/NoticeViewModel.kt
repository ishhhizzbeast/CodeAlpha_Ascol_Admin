package com.example.ascol_admin.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.usecases.UploadNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val uploadNoticeUseCase: UploadNoticeUseCase
) : ViewModel() {


    private val _uploadStatus = MutableStateFlow<String?>(null)
    val uploadStatus: StateFlow<String?> get() = _uploadStatus

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> get() = _isUploading
    fun uploadNotice(title: String, bitmap: Bitmap) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                uploadNoticeUseCase(title, bitmap)
                _uploadStatus.value = "Notice uploaded successfully"
            } catch (e: Exception) {
                _uploadStatus.value = "Failed to upload notice"
            } finally {
                _isUploading.value = false
            }
        }
    }
    fun resetUploadStatus() {
        _uploadStatus.value = null
    }

}