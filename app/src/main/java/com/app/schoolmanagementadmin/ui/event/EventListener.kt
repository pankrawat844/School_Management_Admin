package com.app.schoolmanagementadmin.ui.event

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.NoticeList

interface EventListener {
    fun onStarted()
    fun onSuccess(data: Homework)
    fun onAllNoticeSuccess(data: NoticeList)
    fun onFailure(msg: String)
}