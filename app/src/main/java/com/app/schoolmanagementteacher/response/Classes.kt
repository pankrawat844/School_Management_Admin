package com.app.schoolmanagementteacher.response
import com.google.gson.annotations.SerializedName


data class Classes(
    @SerializedName("response")
    val response: List<Data>? = null
) {
    data class Data(
        @SerializedName("class_id")
        val classId: String?,
        @SerializedName("class_name")
        val className: String?,
        @SerializedName("school_id")
        val schoolId: String?,
        @SerializedName("section_name")
        val sectionName: String?
    )
}