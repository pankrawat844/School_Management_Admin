package com.app.schoolmanagementadmin.network.response


import com.google.gson.annotations.SerializedName

data class Homework(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("response")
    val response: String?,
    @SerializedName("success")
    val success: Boolean?
)