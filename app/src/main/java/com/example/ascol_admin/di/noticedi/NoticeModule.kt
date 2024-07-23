package com.example.ascol_admin.di.noticedi

import com.example.ascol_admin.data.datasource.NoticeRemoteDataSource
import com.example.ascol_admin.data.datasource.NoticeRemoteDataSourceImpl
import com.example.ascol_admin.data.repository.NoticeRepository
import com.example.ascol_admin.data.repository.NoticeRepositoryImpl
import com.example.ascol_admin.domain.model.usecases.UploadNoticeUseCase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoticeModule {

    @Provides
    @Singleton
    fun provideNoticeRemoteDataSource(
        firebaseStorage: FirebaseStorage,
        firebaseDatabase: FirebaseDatabase
    ): NoticeRemoteDataSource {
        return NoticeRemoteDataSourceImpl(firebaseStorage, firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideNoticeRepository(
        noticeRemoteDataSource: NoticeRemoteDataSource
    ): NoticeRepository {
        return NoticeRepositoryImpl(noticeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUploadNoticeUseCase(
        noticeRepository: NoticeRepository
    ): UploadNoticeUseCase {
        return UploadNoticeUseCase(noticeRepository)
    }
}