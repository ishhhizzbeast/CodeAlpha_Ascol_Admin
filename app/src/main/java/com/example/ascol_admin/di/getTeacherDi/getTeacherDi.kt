package com.example.ascol_admin.di.getTeacherDi

import com.example.ascol_admin.data.TeaccherRepository.TeacherRepository
import com.example.ascol_admin.data.TeaccherRepository.TeacherRepositoryImpl
import com.example.ascol_admin.domain.model.usecases.GetTeacherUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object getTeacherDi {

    @Provides
    @Singleton
    fun providegetTeacherRepository():TeacherRepository{
        return TeacherRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGetTeacherUsecases(teacherRepository: TeacherRepository):GetTeacherUseCases{
        return GetTeacherUseCases(teacherRepository)
    }
}