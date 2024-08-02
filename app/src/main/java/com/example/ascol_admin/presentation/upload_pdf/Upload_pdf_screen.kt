package com.example.ascol_admin.presentation.upload_pdf

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ascol_admin.R
import com.example.ascol_admin.presentation.upload_notice.uriToBitmap
import com.example.ascol_admin.presentation.viewmodel.NoticeViewModel
import com.example.ascol_admin.presentation.viewmodel.PdfViewModel

@Preview
@Composable
fun UploadPdfScreen() {
    val viewmodel:PdfViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var isTitleError by remember { mutableStateOf(false) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedFileUri = uri
        selectedFileName = uri?.let { getFileName(context, it) }
    }

    // Observe the upload status
    val uploadStatus by viewmodel.uploadStatus.collectAsState()
    val isUploading by viewmodel.isLoading.collectAsState()

    LaunchedEffect(uploadStatus) {
        uploadStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewmodel.resetLoadingStatus() // Clear status after showing toast
            selectedFileName = null
            selectedFileUri = null
            title = ""
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SelectPdfCard(onClick = {
                    launcher.launch("*/*")
                })
                selectedFileName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = title,
                    onValueChange = {
                        title = it
                        isTitleError = it.isBlank()
                    },
                    isError = isTitleError,
                    label = {
                        Text(text = "Enter the document title")
                    },
                    trailingIcon = {
                        if (isTitleError) {
                            Icon(
                                painter = painterResource(id = R.drawable.error),
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    supportingText = {
                        if (isTitleError) {
                            Text(text = "Title should not be empty")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                              if(selectedFileUri == null){
                                  Toast.makeText(context,"Please select a pdf first",Toast.LENGTH_LONG).show()
                              }
                              else if(title.isBlank()){
                                  Toast.makeText(context,"Please give a title of pdf",Toast.LENGTH_LONG).show()
                              }else{
                                  selectedFileUri?.let {
                                      viewmodel.uploadPdf(title,it)
                                  }
                              }
                    },
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Upload Document")
                }
            }
        }
        if (isUploading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }
    }
}

@Composable
fun SelectPdfCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(height = 160.dp, width = 70.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(MaterialTheme.colorScheme.inversePrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(45.dp),
                        painter = painterResource(id = R.drawable.fileupload),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Select Document", textAlign = TextAlign.Center, fontSize = 16.sp)
            }
        }
    }
}

// Function to get the file name from Uri
fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }
    return fileName
}