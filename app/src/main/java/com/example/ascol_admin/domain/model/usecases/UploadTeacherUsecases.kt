package com.example.ascol_admin.domain.model.usecases

import com.example.ascol_admin.data.addTeacherRepository.addTeacherRepository
import com.example.ascol_admin.domain.model.Teacher
import javax.inject.Inject

class UploadTeacherUsecases @Inject constructor(
    private val uploadTeacherRepository: addTeacherRepository
) {
    suspend operator fun invoke(teacher:Teacher):Result<String>{
        return uploadTeacherRepository.uploadTeacherData(teacher)
    }
}