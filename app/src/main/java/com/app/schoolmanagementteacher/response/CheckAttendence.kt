package com.app.schoolmanagementteacher.response


import com.google.gson.annotations.SerializedName

data class CheckAttendence(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("attendence")
        val attendence: String?,
        @SerializedName("class_id")
        val classId: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("id")
        val id: String?
    )
}