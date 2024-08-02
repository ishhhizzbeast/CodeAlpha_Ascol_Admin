package com.example.ascol_admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ascol_admin.presentation.ScreenList.Screen
import com.example.ascol_admin.presentation.dashboard_screen.DashboardScreen
import com.example.ascol_admin.presentation.deleteNotice.DeleteNotice
import com.example.ascol_admin.presentation.uploadFaculty.AddTeacherScreen
import com.example.ascol_admin.presentation.uploadFaculty.UpdateFaculty
import com.example.ascol_admin.presentation.upload_gallery.UploadGalleryScreen
import com.example.ascol_admin.presentation.upload_notice.UploadNoticeScreen
import com.example.ascol_admin.presentation.upload_pdf.UploadPdfScreen
import com.example.ascol_admin.presentation.viewmodel.NoticeViewModel
import com.example.compose.Ascol_AdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ascol_AdminTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.dashboard.route){
                        composable(Screen.dashboard.route){
                            DashboardScreen(navController)
                        }
                        composable(Screen.uploadNotice.route){
                            UploadNoticeScreen()
                        }
                        composable(Screen.uploadPdf.route){
                            UploadPdfScreen()
                        }
                        composable(Screen.uploadPhotos.route){
                            UploadGalleryScreen()
                        }
                        composable(Screen.updateFaculty.route){
                            UpdateFaculty(navController)
                        }
                        composable(Screen.deleteNotice.route){
                            DeleteNotice()
                        }
                        composable(
                            Screen.addTeacher.route
                        ){

                            AddTeacherScreen(navController)
                        }

                    }
                }
            }
        }
    }
}