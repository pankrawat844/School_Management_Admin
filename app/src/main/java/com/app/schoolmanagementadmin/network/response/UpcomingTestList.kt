package com.app.schoolmanagementadmin.network.response


import com.google.gson.annotations.SerializedName

data class UpcomingTestList(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: List<Response>?,
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
        @SerializedName("info")
        val info: String?,
        @SerializedName("test_name")
        val testName: String?
    )
}