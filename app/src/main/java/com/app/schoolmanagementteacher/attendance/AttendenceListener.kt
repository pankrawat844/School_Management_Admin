package com.app.schoolmanagementteacher.attendance

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.response.StudentList

interface AttendenceListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllStudentSuccess(data:StudentList)
    fun onFailure(msg:String)
}