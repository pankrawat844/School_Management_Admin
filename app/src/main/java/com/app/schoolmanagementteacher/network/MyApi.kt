package com.app.schoolmanagementteacher.network

import com.app.schoolmanagementteacher.response.*
import com.app.schoolmanagementteacher.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyApi {

    companion object{
        operator fun invoke():MyApi
        {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val gson=GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .client(client)
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
    @POST("homework_list.php")
    fun all_homework(
        @Field("incharge_id") incharge_id:String

    ):Call<HomeworkList>

    @Multipart
    @POST("homework.php")
    fun homework_upload(
        @Part("incharge_id") incharge_id: RequestBody,
        @Part("date") date:RequestBody,
        @Part("homework_txt") homework_txt:RequestBody,
        @Part img:MultipartBody.Part,
        @Part("type") type:Int=1
        ):Call<Homework>

    @FormUrlEncoded
    @POST("add_notice.php")
    fun add_notice(
        @Field("incharge_id") incharge_id:String,
        @Field("title") title:String,
        @Field("notice") notice:String
    ):Call<Homework>

    @FormUrlEncoded
    @POST("notice_list.php")
    fun all_notice(
        @Field("incharge_id") incharge_id:String

    ): Call<NoticeList>

    @FormUrlEncoded
    @POST("add_test.php")
    fun add_test(
        @Field("incharge_id") incharge_id:String,
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("info") info:String
    ):Call<Homework>

    @FormUrlEncoded
    @POST("edit_test.php")
    fun update_test(
        @Field("id") id:String,
        @Field("title") title:String,
        @Field("date") date:String,
        @Field("info") info:String
    ):Call<Homework>
    @FormUrlEncoded
    @POST("test_list.php")
    fun all_test(
        @Field("incharge_id") incharge_id:String

    ): Call<UpcomingTestList>

    @FormUrlEncoded
    @POST("student_list.php")
    fun all_student(
        @Field("class_name") class_name: String,
        @Field("section_name") section_name: String


    ): Call<StudentList>

    @FormUrlEncoded
    @POST("attendence.php")
    fun add_attendence(
        @Field("date") date: String,
        @Field("class_name") class_id: String,
        @Field("section_name") notice: String,
        @Field("attendence") attendence: String
    ): Call<Homework>

    @FormUrlEncoded
    @POST("check_attendence.php")
    fun check_attendence(
        @Field("date") date: String,
        @Field("class_name") class_id: String,
        @Field("section_name") notice: String
    ): Call<CheckAttendence>

    @Multipart
    @POST("time_table.php")
    fun upload_timetable(
        @Part("class_name") class_name: RequestBody,
        @Part("section_name") section_name: RequestBody,
        @Part img: MultipartBody.Part,
        @Part("type") type: Int = 1
    ): Call<Homework>

    @FormUrlEncoded
    @POST("timetable_list.php")
    fun get_timetable(
        @Field("class_name") class_name: String,
        @Field("section_name") notice: String
    ): Call<Timetable>


    @Multipart
    @POST("leave.php")
    fun upload_leave(
        @Part("class_name") class_name: RequestBody,
        @Part("section_name") section_name: RequestBody,
        @Part img: MultipartBody.Part,
        @Part("type") type: Int = 1
    ): Call<Homework>

    @FormUrlEncoded
    @POST("leave_detail.php")
    fun get_leave(
        @Field("class_name") class_name: String,
        @Field("section_name") notice: String
    ): Call<Timetable>

    @Multipart
    @POST("fee_info.php")
    fun upload_feeInfo(
        @Part("class_name") class_name: RequestBody,
        @Part("section_name") section_name: RequestBody,
        @Part img: MultipartBody.Part,
        @Part("type") type: Int = 1
    ): Call<Homework>

    @FormUrlEncoded
    @POST("fee_info_detail.php")
    fun get_feeInfo(
        @Field("class_name") class_name: String,
        @Field("section_name") notice: String
    ): Call<Timetable>
}