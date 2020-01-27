package com.app.schoolmanagementteacher.network

import com.app.schoolmanagement.students.network.SafeApiRequest
import com.app.schoolmanagementteacher.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Part

class Repository(val myApi: MyApi):SafeApiRequest() {

    suspend fun getLogin(userid:String,password:String):Call<TeacherLogin>
    {
        return  myApi.teacher_login(userid,password)
    }

    suspend fun sendHomework(@Part("incharge_id") incharge_id: RequestBody,
                             @Part("date") date:RequestBody,
                             @Part("homework_txt") homework_txt:RequestBody,
                            img: MultipartBody.Part):Call<Homework>
    {
        return  myApi.homework_upload(incharge_id, date, homework_txt, img)
    }


    suspend fun allHomework(incharge_id: String):Call<HomeworkList>
    {
        return  myApi.all_homework(incharge_id)
    }

    suspend fun addNotice( incharge_id: String,
                             title:String,
                           notice:String):Call<Homework>
    {
        return  myApi.add_notice(incharge_id, title,notice)
    }

    suspend fun allNotice(incharge_id: String): Call<NoticeList> {
        return myApi.all_notice(incharge_id)
    }

    suspend fun allStudent(class_name: String, section_name: String): Call<StudentList> {
        return myApi.all_student(class_name, section_name)
    }

    suspend fun allTest(incharge_id: String): Call<UpcomingTestList> {
        return myApi.all_test(incharge_id)
    }

    suspend fun addTest( incharge_id: String,
                         title:String,
                           date:String,
                         info:String):Call<Homework>
    {
        return  myApi.add_test(incharge_id, title,date,info)
    }

    suspend fun updateTest(
                        id:String,
                        title:String,
                         date:String,
                         info:String):Call<Homework>
    {
        return  myApi.update_test(id, title,date,info)
    }
    suspend fun addAttendence(
        class_name: String,
        section_name: String,
        date: String,
        attendence: String
    ): Call<Homework> {
        return myApi.add_attendence(date, class_name, section_name, attendence)
    }

    suspend fun checkAttendence(
        class_name: String,
        section_name: String,
        date: String
    ): Call<CheckAttendence> {
        return myApi.check_attendence(date, class_name, section_name)
    }
}