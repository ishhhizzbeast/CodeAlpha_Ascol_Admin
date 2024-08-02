package com.example.ascol_admin.data.pdfrepository

import android.net.Uri

interface PdfRepository {
    suspend fun uploadPdf(pdftitle:String,pdfurl:Uri):String
}