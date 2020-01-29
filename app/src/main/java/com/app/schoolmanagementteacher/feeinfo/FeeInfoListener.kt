package com.app.schoolmanagementteacher.feeinfo

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.Timetable

interface FeeInfoListener {
    fun onStarted()
    fun onImageSuccess(data: Homework)
    fun onPdfSuccess(data: Homework)
    fun onSuccess(data: Timetable)
    fun onFailure(msg: String)
}