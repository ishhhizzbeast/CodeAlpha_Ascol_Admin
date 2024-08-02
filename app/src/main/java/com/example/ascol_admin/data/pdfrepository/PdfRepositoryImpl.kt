package com.example.ascol_admin.data.pdfrepository

import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class PdfRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) :PdfRepository{
    override suspend fun uploadPdf(pdftitle: String, pdfurl: Uri):String {
        //generate filename by random key generator
        val filename = "${UUID.randomUUID()}.pdf"

        // Upload the PDF to Firebase Storage
        val storageref = firebaseStorage.reference.child("pdfs/$filename")
        val uploadTask = storageref.putFile(pdfurl).await()
        val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

        // Create a unique key for the PDF entry in the database
        val pdfRef = firebaseDatabase.getReference("pdfs").push()
        val key = pdfRef.key

        val pdfData = mapOf(
            "pdftitle" to pdftitle,
            "pdfurl"  to downloadUrl

        )
        pdfRef.setValue(pdfData).await()

        return key ?: throw Exception("Failed to get database key")
    }
}