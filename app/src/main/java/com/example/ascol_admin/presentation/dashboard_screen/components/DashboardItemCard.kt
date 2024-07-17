package com.example.ascol_admin.presentation.dashboard_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ascol_admin.R
import com.example.ascol_admin.presentation.dashboard_screen.Dashboard


@Composable
fun DashboardItemCard(dashboard: Dashboard) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(height = 160.dp, width = 70.dp)
        ,
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
                        painter = dashboard.imagePainter,
                        contentDescription = "", contentScale = ContentScale.FillBounds)
                }
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
            Box(modifier = Modifier.padding(10.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = dashboard.title, textAlign = TextAlign.Center, fontSize = 16.sp)
            }
        }
    }
}