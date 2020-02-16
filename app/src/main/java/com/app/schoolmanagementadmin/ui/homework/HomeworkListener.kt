package com.app.schoolmanagementadmin.ui.homework

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.HomeworkList

interface HomeworkListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllHomeworkSuccess(data:HomeworkList)
    fun onFailure(msg:String)
}