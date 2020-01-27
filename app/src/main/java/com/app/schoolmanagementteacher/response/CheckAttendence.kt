package com.app.schoolmanagementteacher.response


import com.google.gson.annotations.SerializedName

data class CheckAttendence(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response?>?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("attendence")
        val attendence: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("roll_no")
        val rollNo: String?
    )
}