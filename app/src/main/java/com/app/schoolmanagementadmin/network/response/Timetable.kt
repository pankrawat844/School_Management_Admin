package com.app.schoolmanagementadmin.network.response


import com.google.gson.annotations.SerializedName

data class Timetable(
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Response(
        @SerializedName("class_id")
        val classId: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("img_path")
        val imgPath: String?,
        @SerializedName("is_pdf")
        val isPdf: String?,
        @SerializedName("pdf_path")
        val pdfPath: String?
    )
}