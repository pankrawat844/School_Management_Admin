package com.app.schoolmanagementteacher.network

import android.telecom.Call
import com.app.schoolmanagement.students.network.SafeApiRequest
import com.app.schoolmanagementteacher.response.TeacherLogin

class Repository(val myApi: MyApi):SafeApiRequest() {

    suspend fun getLogin(userid:String,password:String):retrofit2.Call<TeacherLogin>
    {
        return  myApi.teacher_login(userid,password)
    }
}