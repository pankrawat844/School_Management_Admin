package com.app.schoolmanagementteacher.response
import com.google.gson.annotations.SerializedName


data class StudentList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response>?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("address")
        val address: String?,
        @SerializedName("class_id")
        val classId: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("guardian_name")
        val guardianName: String?,
        @SerializedName("mobile")
        val mobile: Any?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("roll_no")
        val rollNo: String?,
        @SerializedName("school_id")
        val schoolId: String?,
        @SerializedName("student_id")
        val studentId: String?,
        @SerializedName("attendence")
        var attendence: String?
    )
}