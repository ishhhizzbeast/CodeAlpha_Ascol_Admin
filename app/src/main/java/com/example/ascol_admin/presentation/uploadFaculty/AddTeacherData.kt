package com.example.ascol_admin.presentation.uploadFaculty

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ascol_admin.R
import com.example.ascol_admin.domain.model.Teacher
import com.example.ascol_admin.presentation.viewmodel.AddTeacherViewMdoel
import java.io.IOException
import java.io.InputStream
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTeacherScreen(navController: NavController,) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var post by remember { mutableStateOf("") }
    var isTitleError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPostError by remember { mutableStateOf(false) }
    val viewModel:AddTeacherViewMdoel = hiltViewModel()


    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Select Department") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val departmentOptions = listOf("Botany", "Chemistry", "Computer", "Environment", "Physics", "Zoology", "Microbiology", "Mathematics")

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri.toString()
    }
    val uploadStatus by viewModel.uploadStatus.collectAsState()
    val isUploading by viewModel.isLoading.collectAsState()

    LaunchedEffect(uploadStatus) {
        uploadStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetUploadingStatus() // Clear status after showing toast
            selectedImageUri = null
            name = ""
            email = ""
            post = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Teacher") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer)
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(MaterialTheme.colorScheme.inverseOnSurface, shape = CircleShape)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
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
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(16.dp))
                                .border(
                                    4.dp,
                                    rainbowColorsBrush,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .height(500.dp),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.addprofile),
                            contentDescription = "Add Image",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { launcher.launch("image/*") }) {
                    Text(text = "Add Image")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isTitleError = it.isBlank()
                    },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isTitleError,
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
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailError = it.isBlank()
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isEmailError,
                    trailingIcon = {
                        if (isEmailError) {
                            Icon(
                                painter = painterResource(id = R.drawable.error),
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    supportingText = {
                        if (isEmailError) {
                            Text(text = "Email should not be empty")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = post,
                    onValueChange = {
                        post = it
                        isPostError = it.isBlank()
                    },
                    label = { Text("Post") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isPostError,
                    trailingIcon = {
                        if (isPostError) {
                            Icon(
                                painter = painterResource(id = R.drawable.error),
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    supportingText = {
                        if (isPostError) {
                            Text(text = "Title should not be empty")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = {},
                        label = { Text("Categories") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        departmentOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(text = selectionOption) },
                                onClick = {
                                    expanded = false
                                    selectedOptionText = selectionOption
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (selectedOptionText == "Select Department") {
                            Toast.makeText(context, "Please select a department first", Toast.LENGTH_LONG).show()
                        } else if (name.isBlank() && post.isBlank()) {
                            Toast.makeText(context, "Please fill above empty field first", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.uploadTeacherData(
                                teacher = Teacher(
                                    key = "${UUID.randomUUID()}",
                                    department = selectedOptionText,
                                    email = email,
                                    image = selectedImageUri ?: "",
                                    name = name,
                                    post = post
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inversePrimary)
                ) {
                    Text(text = "Add Teacher")
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
}
