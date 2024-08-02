package com.example.ascol_admin.domain.model.usecases

import com.example.ascol_admin.data.TeaccherRepository.TeacherRepository
import com.example.ascol_admin.domain.model.Teacher
import javax.inject.Inject

class GetTeacherUseCases @Inject constructor(
    private val teacherRepository: TeacherRepository
){
    suspend operator fun invoke(department:String):List<Teacher>{
        return teacherRepository.getTeachersByDepartment(department)
    }
}