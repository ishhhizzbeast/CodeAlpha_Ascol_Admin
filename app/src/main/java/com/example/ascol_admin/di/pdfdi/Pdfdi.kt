package com.example.ascol_admin.di.pdfdi

import com.example.ascol_admin.data.pdfrepository.PdfRepository
import com.example.ascol_admin.data.pdfrepository.PdfRepositoryImpl
import com.example.ascol_admin.domain.model.usecases.UploadPdfUsecase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Pdfdi {
    @Provides
    @Singleton
    fun providePdfRepository(
        firebaseStorage: FirebaseStorage,
        firebaseDatabase: FirebaseDatabase
    ):PdfRepository{
        return PdfRepositoryImpl(firebaseDatabase,firebaseStorage)
    }
    @Provides
    @Singleton
    fun providePdfUsecase(pdfRepository: PdfRepository):UploadPdfUsecase{
        return UploadPdfUsecase(pdfRepository)
    }
}