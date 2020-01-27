package com.app.schoolmanagementteacher.timetable

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList

interface TimeTableListener {
    fun onStarted()
    fun onImageSuccess(data:Homework)
    fun onPdfSuccess(data:HomeworkList)
    fun onFailure(msg:String)
}