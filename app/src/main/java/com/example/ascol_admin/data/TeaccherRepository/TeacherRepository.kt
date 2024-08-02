package com.example.ascol_admin.data.TeaccherRepository

import com.example.ascol_admin.domain.model.Teacher

interface TeacherRepository {
    suspend fun getTeachersByDepartment(department: String): List<Teacher>


}