package com.example.ascol_admin.presentation.deleteNotice

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ascol_admin.presentation.deleteNotice.components.PostToDelete
import com.example.ascol_admin.presentation.viewmodel.GetAndDeleteNotice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNotice() {
    val viewmodel:GetAndDeleteNotice = hiltViewModel()
    val context = LocalContext.current
    val notices = viewmodel.notices.collectAsState().value
    val deleteStatus = viewmodel.deleteStatus.collectAsState().value

    LaunchedEffect(deleteStatus) {
        deleteStatus?.let {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewmodel.reloadDeleteStatus()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Notices")}, colors = TopAppBarDefaults.topAppBarColors(
                MaterialTheme.colorScheme.primaryContainer))
        }
    ){paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(notices) { notice ->
                PostToDelete(
                    noticeTitle = notice.title,
                    noticeImageUrl = notice.imageURL,
                    onDeleteClick = {
                        viewmodel.deleteNotice(notice.key)
                    }

                )
            }
        }
    }

}