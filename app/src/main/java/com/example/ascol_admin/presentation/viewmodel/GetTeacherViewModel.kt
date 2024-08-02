package com.example.ascol_admin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ascol_admin.domain.model.Teacher
import com.example.ascol_admin.domain.model.usecases.GetTeacherUseCases
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GetTeacherViewModel @Inject constructor(
    private val getTeacherUsecases: GetTeacherUseCases
):ViewModel() {
    private val _teachersByDepartment = MutableStateFlow<Map<String, List<Teacher>>>(emptyMap())
    val teachersByDepartment: StateFlow<Map<String, List<Teacher>>> = _teachersByDepartment


     fun fetchTeachers(departments:List<String>) {
        viewModelScope.launch {
            val result = mutableMapOf<String, List<Teacher>>()
            departments.forEach { department ->
                val teachers = getTeacherUsecases(department)
                result[department] = teachers
            }
            _teachersByDepartment.value = result
        }
    }
    private val _deleteStatus = MutableStateFlow<String?>(null)
    val deleteStatus: StateFlow<String?> = _deleteStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    fun fetchTeachersForDepartment(department: String) {
        viewModelScope.launch {
            val departmentSnapshot = FirebaseDatabase.getInstance().getReference("teachers").child(department).get().await()
            val teachers = departmentSnapshot.children.mapNotNull { it.getValue(Teacher::class.java) }
            val updatedMap = _teachersByDepartment.value.toMutableMap()
            updatedMap[department] = teachers
            _teachersByDepartment.value = updatedMap
        }
    }
    fun deleteTeacher(department: String, key: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val databaseReference = FirebaseDatabase.getInstance().getReference("teachers")
                val departmentSnapshot = databaseReference.child(department)
                departmentSnapshot.child(key).removeValue().await()
                fetchTeachersForDepartment(department)
                _deleteStatus.value = "Teacher deleted!"
            } catch (e: IOException) {
                _deleteStatus.value = "Failed to delete teacher"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun resetDeleteStatus() {
        _deleteStatus.value = null
    }
}