package com.example.ascol_admin.domain.model.usecases

import android.net.Uri
import com.example.ascol_admin.data.pdfrepository.PdfRepository
import javax.inject.Inject

class UploadPdfUsecase @Inject constructor(
    private val pdfRepository: PdfRepository
) {
    suspend operator fun invoke(pdftitle:String,pdfUrl:Uri):String{
        return pdfRepository.uploadPdf(pdftitle,pdfUrl)
    }
}