package com.example.ascol_admin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.Notice
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GetAndDeleteNotice @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    private val _notices = MutableStateFlow<List<Notice>>(emptyList())
    val notices = _notices.asStateFlow()

    private val _deleteStatus = MutableStateFlow<String?>(null)
    val deleteStatus = _deleteStatus.asStateFlow()

    init {
        fetchNotices()
    }

    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO){
            val snapshot = firebaseDatabase.getReference("Notices").get().await()
            val noticesList = snapshot.children.mapNotNull {
                it.getValue(Notice::class.java)

            }
            _notices.value = noticesList
        }
    }
    fun deleteNotice(notice:String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                firebaseDatabase.getReference("Notices").child(notice).removeValue().await()
                _deleteStatus.value = "Deleted Notice"
                fetchNotices()
            }catch (e:IOException){
                _deleteStatus.value = "Try Again! some error occurred"
            }


        }

    }
    fun reloadDeleteStatus(){
        _deleteStatus.value = null
    }
}