package com.app.schoolmanagementteacher.homework

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.TeacherLogin

interface HomeworkListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllHomeworkSuccess(data:HomeworkList)
    fun onFailure(msg:String)
}