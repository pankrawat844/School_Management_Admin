package com.app.schoolmanagementadmin.ui.attendance

import com.app.schoolmanagementadmin.network.response.CheckAttendence
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.StudentList

interface AttendenceListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllStudentSuccess(data: StudentList)
    fun onCheckAttendence(data: CheckAttendence)
    fun onCheckAttendenceFailour(msg: String)
    fun onFailure(msg: String)
}