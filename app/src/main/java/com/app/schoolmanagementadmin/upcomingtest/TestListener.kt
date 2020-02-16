package com.app.schoolmanagementadmin.upcomingtest

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.UpcomingTestList

interface TestListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllTestSuccess(data:UpcomingTestList)
    fun onFailure(msg:String)
}