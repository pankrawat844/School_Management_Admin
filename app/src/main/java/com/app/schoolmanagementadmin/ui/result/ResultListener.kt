package com.app.schoolmanagementadmin.ui.result

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.StudentList
import com.app.schoolmanagementadmin.network.response.UpcomingTestList

interface ResultListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllStudentSuccess(data: StudentList)
    fun onAllTestSuccess(data: UpcomingTestList)
    fun onFailure(msg: String)
}