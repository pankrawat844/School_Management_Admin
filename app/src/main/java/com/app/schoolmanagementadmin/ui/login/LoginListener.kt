package com.app.schoolmanagementadmin.ui.login

import com.app.schoolmanagementadmin.network.response.TeacherLogin

interface LoginListener {
    fun onStarted()
    fun onSuccess(data:TeacherLogin.Response)
    fun onFailure(msg:String)
}