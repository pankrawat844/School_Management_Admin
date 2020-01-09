package com.app.schoolmanagementteacher.network

import com.app.schoolmanagementteacher.response.TeacherLogin
import com.app.schoolmanagementteacher.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    companion object{
        operator fun invoke():MyApi
        {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
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
}