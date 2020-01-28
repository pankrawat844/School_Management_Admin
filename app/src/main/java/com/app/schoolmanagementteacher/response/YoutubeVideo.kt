package com.app.schoolmanagementteacher.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class YoutubeVideo(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("regionCode")
    val regionCode: String
) : Serializable {


    data class Item(
        @SerializedName("etag")
        val etag: String,
        @SerializedName("id")
        val id: Id,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("snippet")
        val snippet: Snippet
    ) : Serializable {

        data class Id(
            @SerializedName("kind")
            val kind: String,
            @SerializedName("videoId")
            val videoId: String
        ) : Serializable

        data class Snippet(
            @SerializedName("channelId")
            val channelId: String,
            @SerializedName("channelTitle")
            val channelTitle: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("liveBroadcastContent")
            val liveBroadcastContent: String,
            @SerializedName("publishedAt")
            val publishedAt: String,
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails,
            @SerializedName("title")
            val title: String
        ) : Serializable {
            data class Thumbnails(
                @SerializedName("default")
                val default: Default,
                @SerializedName("high")
                val high: High,
                @SerializedName("medium")
                val medium: Medium
            ) : Serializable {
                data class Default(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                ) : Serializable

                data class High(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                ) : Serializable

                data class Medium(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                ) : Serializable
            }
        }
    }

    data class PageInfo(
        @SerializedName("resultsPerPage")
        val resultsPerPage: Int,
        @SerializedName("totalResults")
        val totalResults: Int
    )


}