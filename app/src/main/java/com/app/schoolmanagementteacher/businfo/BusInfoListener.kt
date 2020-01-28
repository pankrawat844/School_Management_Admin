package com.app.schoolmanagementteacher.businfo

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.Timetable

interface BusInfoListener {
    fun onStarted()
    fun onImageSuccess(data:Homework)
    fun onPdfSuccess(data:Homework)
    fun onSuccess(data:Timetable)
    fun onFailure(msg:String)
}