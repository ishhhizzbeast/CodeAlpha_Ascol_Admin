package com.example.ascol_admin.data.addTeacherRepository

import com.example.ascol_admin.data.datasource.TeacherDataSource.TeacherDataSource
import com.example.ascol_admin.domain.model.Teacher
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class addTeacherRepositoryImpl @Inject constructor(
    private val addTeacherDataSource: TeacherDataSource
) :addTeacherRepository {
    override suspend fun uploadTeacherData(teacher: Teacher): Result<String> {
        return addTeacherDataSource.uploadTeacherData(teacher)
    }

}

