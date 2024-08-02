package com.example.ascol_admin.data.addTeacherRepository

import com.example.ascol_admin.domain.model.Teacher

interface addTeacherRepository {
    suspend fun uploadTeacherData(teacher:Teacher):Result<String>

}