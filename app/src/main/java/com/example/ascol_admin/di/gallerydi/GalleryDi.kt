package com.example.ascol_admin.di.gallerydi

import com.example.ascol_admin.data.galleryRepository.GalleryRepository
import com.example.ascol_admin.data.galleryRepository.GalleryRepositoryImpl
import com.example.ascol_admin.domain.model.usecases.UploadGalleryImageUsecase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GalleryDi {
    @Provides
    @Singleton
    fun provideGalleryRepository(
        firebaseDatabase: FirebaseDatabase,
        firebaseStorage: FirebaseStorage
    ):GalleryRepository{
        return GalleryRepositoryImpl(firebaseStorage, firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideUploadGalleryImageUseCases(
        repository:GalleryRepository
    ):UploadGalleryImageUsecase{
        return UploadGalleryImageUsecase(repository)
    }
}