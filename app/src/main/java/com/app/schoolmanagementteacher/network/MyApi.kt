package com.app.schoolmanagementteacher.network

import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.TeacherLogin
import com.app.schoolmanagementteacher.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    companion object{
        operator fun invoke():MyApi
        {
            val gson=GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.base_url)
                .build()
                .create(MyApi::class.java)
        }
    }

    @FormUrlEncoded
    @POST("teacher_login.php")
    fun teacher_login(
        @Field("userid") teacherid:String,
        @Field("password") password:String,
        @Field("school_id")schoolid:String="1"
    ):Call<TeacherLogin>


    @FormUrlEncoded
    @POST("teacher_login.php")
    fun all_homework(
        @Field("incharge_id") incharge_id:String

    ):Call<HomeworkList>

    @Multipart
    @POST("homework.php")
    fun homework_upload(
        @Part("incharge_id") incharge_id: RequestBody,
        @Part("date") date:RequestBody,
        @Part("homework_txt") homework_txt:RequestBody,
        @Part img:MultipartBody.Part
    ):Call<Homework>
}