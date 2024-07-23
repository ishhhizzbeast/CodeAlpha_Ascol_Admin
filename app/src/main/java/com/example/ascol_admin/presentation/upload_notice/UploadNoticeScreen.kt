package com.example.ascol_admin.presentation.upload_notice

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ascol_admin.R
import com.example.ascol_admin.presentation.viewmodel.NoticeViewModel
import java.io.IOException
import java.io.InputStream


@Composable
fun UploadNoticeScreen() {
    val viewModel: NoticeViewModel = hiltViewModel()
    var title by remember { mutableStateOf("") }
    var isTitleError by remember { mutableStateOf(false) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedBitmap = uri?.let { uri ->
            uriToBitmap(context, uri)
        }
    }

    // Observe the upload status
    val uploadStatus by viewModel.uploadStatus.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()

    LaunchedEffect(uploadStatus) {
        uploadStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetUploadStatus() // Clear status after showing toast
            selectedBitmap = null
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
                SelectImageCard(onClick = {
                    launcher.launch("image/*")
                })
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
                        Text(text = "Enter the notice title")
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
                            Text(text = "title should not be empty")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (title.isBlank()) {
                            isTitleError = true
                        } else if (selectedBitmap == null) {
                            Toast.makeText(context,"Please select image",Toast.LENGTH_LONG).show()
                        }
                        else{
                            isTitleError = false
                            selectedBitmap?.let {
                                viewModel.uploadNotice(title, it)
                            }
                        }
                    },
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Upload notice")
                }
                Spacer(modifier = Modifier.height(8.dp))
                val rainbowColorsBrush = remember {
                    Brush.sweepGradient(
                        listOf(
                            Color(0xFF9575CD),
                            Color(0xFFBA68C8),
                            Color(0xFFE57373),
                            Color(0xFFFFB74D),
                            Color(0xFFFFF176),
                            Color(0xFFAED581),
                            Color(0xFF4DD0E1),
                            Color(0xFF9575CD)
                        )
                    )
                }

                selectedBitmap?.let { bitmap ->
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .border(4.dp,rainbowColorsBrush, shape = RoundedCornerShape(16.dp))
                            .height(500.dp)
                        ,
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
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
fun SelectImageCard(onClick:()->Unit) {
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
                    .weight(1f)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(MaterialTheme.colorScheme.inversePrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(modifier = Modifier.size(45.dp),
                        painter = painterResource(id = R.drawable.picture),
                        contentDescription = "", contentScale = ContentScale.FillBounds)
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
            Box(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "Select Image", textAlign = TextAlign.Center, fontSize = 16.sp)
            }
        }
    }
}

// Function to convert URI to Bitmap
fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    return try {
        inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}