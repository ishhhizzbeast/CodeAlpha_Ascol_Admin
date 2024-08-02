package com.example.ascol_admin.presentation.ScreenList

sealed class Screen(val route:String) {
    data object dashboard:Screen("dashboard_screen")
    data object uploadNotice:Screen("upload_notice_screen")
    data object uploadPdf:Screen("upload_pdf_screen")
    data object uploadPhotos:Screen("upload_photos_screen")
    data object deleteNotice:Screen("delete_notice_screen")
    data object updateFaculty:Screen("update_faculty_screen")
    data object addTeacher:Screen("add_teacher_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}