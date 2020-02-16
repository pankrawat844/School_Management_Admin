package com.app.schoolmanagementadmin.ui.businfo

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.Timetable

interface BusInfoListener {
    fun onStarted()
    fun onImageSuccess(data:Homework)
    fun onPdfSuccess(data:Homework)
    fun onSuccess(data:Timetable)
    fun onFailure(msg:String)
}