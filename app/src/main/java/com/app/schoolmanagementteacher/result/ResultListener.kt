package com.app.schoolmanagementteacher.result

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList
import com.app.schoolmanagementteacher.response.UpcomingTestList

interface ResultListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllStudentSuccess(data: StudentList)
    fun onAllTestSuccess(data: UpcomingTestList)
    fun onFailure(msg: String)
}