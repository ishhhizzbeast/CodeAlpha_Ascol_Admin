package com.example.ascol_admin.presentation.uploadFaculty.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ascol_admin.R


@Preview
@Composable
fun NodataFoundCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        elevation = CardDefaults.cardElevation(defaultElevation =8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nodatafound), // Replace with your icon resource
                    contentDescription = "no data found image",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No data found!",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}