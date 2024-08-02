package com.example.ascol_admin.data.datasource.TeacherDataSource

import com.example.ascol_admin.domain.model.Teacher

interface TeacherDataSource {
    suspend fun uploadTeacherData(teacher:Teacher):Result<String>
}