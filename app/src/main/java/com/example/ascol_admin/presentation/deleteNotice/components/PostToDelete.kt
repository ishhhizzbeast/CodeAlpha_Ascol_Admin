package com.example.ascol_admin.presentation.deleteNotice.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.ascol_admin.R



@Composable
fun PostToDelete(
    noticeTitle: String,
    noticeImageUrl: String?,
    onDeleteClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete Notice") },
            text = { Text("Are you sure you want to delete this notice?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClick()
                        showDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f), RoundedCornerShape(8.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Admin Image
                AdminImage()

                Spacer(modifier = Modifier.width(16.dp))

                // Admin Title and Notice Title
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ascol Admin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = noticeTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Delete Button
                IconButton(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Notice",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Notice Image
            if (!noticeImageUrl.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(noticeImageUrl)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = "Notice Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun AdminImage() {
    Image(
        painter = painterResource(id = R.drawable.ascol_logo),
        contentDescription = "Admin Image",
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape),
        contentScale = ContentScale.Crop
    )
}
