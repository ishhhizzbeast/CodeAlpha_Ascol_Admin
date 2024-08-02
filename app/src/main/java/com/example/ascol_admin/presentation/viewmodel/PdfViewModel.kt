package com.example.ascol_admin.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.usecases.UploadPdfUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val uploadPdfUsecase: UploadPdfUsecase
):ViewModel(){
    private var _uploadStatus = MutableStateFlow<String?>(null)
    var uploadStatus = _uploadStatus.asStateFlow()

    private var _isLoading  = MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()

    fun uploadPdf(pdftitle:String,pdfUrl:Uri){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                uploadPdfUsecase(pdftitle,pdfUrl)
                _uploadStatus.value = "Pdf uploaded Successfully"
            }catch (e:IOException){
                _uploadStatus.value = "Some error occured"
            }finally {
                _isLoading.value = false
            }
        }
    }
    fun resetLoadingStatus(){
        _uploadStatus.value = null
    }
}