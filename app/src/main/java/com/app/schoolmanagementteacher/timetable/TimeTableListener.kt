package com.app.schoolmanagementteacher.timetable

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.Timetable

interface TimeTableListener {
    fun onStarted()
    fun onImageSuccess(data:Homework)
    fun onPdfSuccess(data:Homework)
    fun onSuccess(data:Timetable)
    fun onFailure(msg:String)
}