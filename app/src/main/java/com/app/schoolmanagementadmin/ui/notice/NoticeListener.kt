package com.app.schoolmanagementadmin.ui.notice

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.NoticeList

interface NoticeListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllNoticeSuccess(data:NoticeList)
    fun onFailure(msg:String)
}