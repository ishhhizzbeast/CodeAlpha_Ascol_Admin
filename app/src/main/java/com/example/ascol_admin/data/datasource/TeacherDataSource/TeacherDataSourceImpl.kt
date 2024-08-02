package com.example.ascol_admin.data.datasource.TeacherDataSource

import android.net.Uri
import com.example.ascol_admin.domain.model.Teacher
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TeacherDataSourceImpl @Inject constructor(
    private  val firebaseDatabase: FirebaseDatabase,
    private  val firebaseStorage: FirebaseStorage
) :TeacherDataSource {
    override suspend fun uploadTeacherData(teacher: Teacher): Result<String> {
        return try {
            val storageRef = firebaseStorage.reference.child("teachers/${teacher.department}/${UUID.randomUUID()}.jpg")

            val uri = Uri.parse(teacher.image)
            val uploadTask = storageRef.putFile(uri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

            val teacherRef = firebaseDatabase.getReference("teachers").child(teacher.department).push()
            val key = teacherRef.key
            val teacherData = mapOf(
                "name" to teacher.name,
                "image" to downloadUrl,
                "email" to teacher.email,
                "post" to teacher.post,
                "department" to teacher.department,
                "key" to key
            )

            teacherRef.setValue(teacherData).await()
            Result.success(key ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

