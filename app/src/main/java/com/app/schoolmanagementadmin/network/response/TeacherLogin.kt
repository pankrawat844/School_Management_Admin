package com.app.schoolmanagementadmin.network.response


import com.google.gson.annotations.SerializedName

data class TeacherLogin(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("class_name")
        val className: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("is_incharge")
        val isIncharge: String?,
        @SerializedName("is_staff")
        val isStaff: String?,
        @SerializedName("is_teacher")
        val isTeacher: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("section_name")
        val sectionName: String?,
        @SerializedName("userid")
        val userid: String?
    )
}