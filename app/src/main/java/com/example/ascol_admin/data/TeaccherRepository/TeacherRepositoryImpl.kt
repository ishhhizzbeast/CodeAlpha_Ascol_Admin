package com.example.ascol_admin.data.TeaccherRepository

import com.example.ascol_admin.domain.model.Teacher
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class TeacherRepositoryImpl : TeacherRepository {
    private val db = FirebaseDatabase.getInstance().reference

    override suspend fun getTeachersByDepartment(department: String): List<Teacher> {
        val teachers = mutableListOf<Teacher>()
        val snapshot = db.child("teachers").child(department).get().await()

        for (dataSnapshot in snapshot.children) {
            val teacher = dataSnapshot.getValue<Teacher>()
            if (teacher != null) {
                teachers.add(teacher)
            }
        }

        return teachers
    }
}
