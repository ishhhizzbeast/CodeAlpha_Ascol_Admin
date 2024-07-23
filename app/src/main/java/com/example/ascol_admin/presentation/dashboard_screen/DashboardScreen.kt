package com.example.ascol_admin.presentation.dashboard_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ascol_admin.R
import com.example.ascol_admin.presentation.dashboard_screen.components.DashboardItemCard
import com.example.compose.primaryContainerLight
import com.example.compose.primaryDark
import com.example.compose.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    modifier:Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Ascol Admin") }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer))
        }
    ) {
        val dashboard = listOf(
            Dashboard("upload notice", painterResource(id = R.drawable.notice)),
            Dashboard("upload pdf", painterResource(id = R.drawable.fileupload)),
            Dashboard("upload Gallery", painterResource(id = R.drawable.picture)),
            Dashboard("upload faculty", painterResource(id = R.drawable.upload)),
            Dashboard("Delete notice", painterResource(id = R.drawable.delete)),
        )
        LazyVerticalGrid(
            modifier = modifier.padding(it),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(dashboard.size){
                DashboardItemCard(dashboard = dashboard[it],navController,)
            }
        }
    }
}