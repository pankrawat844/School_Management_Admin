package com.app.schoolmanagementteacher.login

import com.app.schoolmanagementteacher.response.TeacherLogin

interface LoginListener {
    fun onStarted()
    fun onSuccess(data:TeacherLogin.Response)
    fun onFailure(msg:String)
}