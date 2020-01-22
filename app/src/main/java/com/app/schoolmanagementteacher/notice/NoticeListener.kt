package com.app.schoolmanagementteacher.notice

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList

interface NoticeListener {
    fun onStarted()
    fun onSuccess(data:Homework)
    fun onAllNoticeSuccess(data:NoticeList)
    fun onFailure(msg:String)
}