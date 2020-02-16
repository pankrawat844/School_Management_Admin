package com.app.schoolmanagementadmin.ui.complaint

import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.NoticeList

interface ComplaintListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllNoticeSuccess(data:NoticeList)
    fun onFailure(msg:String)
}