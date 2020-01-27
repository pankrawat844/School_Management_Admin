package com.app.schoolmanagementteacher.upcomingtest

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.response.UpcomingTestList

interface TestListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllTestSuccess(data:UpcomingTestList)
    fun onFailure(msg:String)
}