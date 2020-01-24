package com.app.schoolmanagementteacher.attendance

import com.app.schoolmanagementteacher.response.CheckAttendence
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList

interface AttendenceListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllStudentSuccess(data: StudentList)
    fun onCheckAttendence(data: CheckAttendence)
    fun onCheckAttendenceFailour(msg: String)
    fun onFailure(msg: String)
}