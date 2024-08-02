package com.example.ascol_admin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.Teacher
import com.example.ascol_admin.domain.model.usecases.UploadTeacherUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddTeacherViewMdoel @Inject constructor(
    private val uploadTeacherUsecases: UploadTeacherUsecases
):ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uploadStatus = MutableStateFlow<String?>(null)
    val uploadStatus: StateFlow<String?> = _uploadStatus

    fun uploadTeacherData(teacher:Teacher){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                uploadTeacherUsecases(teacher)
                _uploadStatus.value = "Teacher data uploaded!"
            }catch (e:IOException){
                _uploadStatus.value = "Something went wrong"
            }finally {
                _isLoading.value = false
            }

        }
    }
    fun resetUploadingStatus() {
        _uploadStatus.value = null
    }


}