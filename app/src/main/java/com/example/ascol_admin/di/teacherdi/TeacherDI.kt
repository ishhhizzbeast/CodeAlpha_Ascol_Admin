package com.example.ascol_admin.di.teacherdi

import com.example.ascol_admin.data.addTeacherRepository.addTeacherRepository
import com.example.ascol_admin.data.addTeacherRepository.addTeacherRepositoryImpl
import com.example.ascol_admin.data.datasource.TeacherDataSource.TeacherDataSource
import com.example.ascol_admin.data.datasource.TeacherDataSource.TeacherDataSourceImpl
import com.example.ascol_admin.domain.model.usecases.UploadTeacherUsecases
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeacherDI {
    @Provides
    @Singleton
    fun provideUploadTeacherRepository(addTeacherDataSource: TeacherDataSource):addTeacherRepository{
        return addTeacherRepositoryImpl(addTeacherDataSource)
    }

    @Provides
    @Singleton
    fun provideUploadTeacherUsecases(repository: addTeacherRepository):UploadTeacherUsecases{
        return UploadTeacherUsecases(repository)
    }

    @Provides
    @Singleton
    fun provideAddTeacherDataSource(
        firebaseDatabase: FirebaseDatabase,
        firebaseStorage: FirebaseStorage
    ):TeacherDataSource{
        return TeacherDataSourceImpl(firebaseDatabase,firebaseStorage)
    }
}