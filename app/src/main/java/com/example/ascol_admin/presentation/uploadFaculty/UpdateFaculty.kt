package com.example.ascol_admin.presentation.uploadFaculty

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ascol_admin.presentation.ScreenList.Screen
import com.example.ascol_admin.presentation.uploadFaculty.component.NodataFoundCard
import com.example.ascol_admin.presentation.uploadFaculty.component.TeacherCard
import com.example.ascol_admin.presentation.viewmodel.GetTeacherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFaculty(navController: NavController) {
    val viewmodel:GetTeacherViewModel = hiltViewModel()
    val teacherBydepartment = viewmodel.teachersByDepartment.collectAsState()
    val deleteStatus by viewmodel.deleteStatus.collectAsState()
    LaunchedEffect(Unit) {
        val departments = listOf(
            "Botany", "Chemistry", "Computer", "Environment", "Physics",
            "Zoology", "Microbiology", "Mathematics"
        )
        viewmodel.fetchTeachers(departments)
    }
    val context = LocalContext.current
    LaunchedEffect(deleteStatus) {
        deleteStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewmodel.resetDeleteStatus()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Faculty") },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.addTeacher.route)}) {
                Icon(Icons.Filled.Add, contentDescription = "Add Teacher")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val departments = listOf(
                "Botany", "Chemistry", "Computer", "Environment", "Physics",
                "Zoology", "Microbiology", "Mathematics"
            )

            departments.forEach { department ->
                item {
                    Text(
                        text = department,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                val teachers = teacherBydepartment.value[department] ?: emptyList()

                if (teachers.isEmpty()) {
                    item { NodataFoundCard() }
                } else {
                    items(teachers) { teacher ->
                        TeacherCard(teacher = teacher, onDeleteConfirm = {
                            viewmodel.deleteTeacher(teacher.department,teacher.key,)
                        })
                    }
                }
            }
        }
    }
}