package com.app.schoolmanagementteacher.event

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.NoticeList

interface EventListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllNoticeSuccess(data: NoticeList)
    fun onFailure(msg: String)
}