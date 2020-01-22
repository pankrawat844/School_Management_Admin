package com.app.schoolmanagementteacher.response


import com.google.gson.annotations.SerializedName

data class NoticeList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response>?=null,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("date")
        val date: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("incharge_id")
        val inchargeId: String?,
        @SerializedName("notice")
        val notice: String?,
        @SerializedName("title")
        val title: String?
    )
}