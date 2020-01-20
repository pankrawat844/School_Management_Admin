package com.app.schoolmanagementteacher.response


import com.google.gson.annotations.SerializedName

data class HomeworkList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response?>?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("date")
        val date: String?,
        @SerializedName("homework_img")
        val homeworkImg: String?,
        @SerializedName("homework_txt")
        val homeworkTxt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("incharge_id")
        val inchargeId: String?
    )
}