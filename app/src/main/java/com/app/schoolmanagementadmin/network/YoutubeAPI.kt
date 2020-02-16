package com.app.schoolmanagementadmin.network

import com.app.schoolmanagementadmin.BuildConfig
import com.app.schoolmanagementadmin.network.response.YoutubeVideo
import com.app.schoolmanagementadmin.network.response.YoutubeVideoRelated
import com.app.schoolmanagementadmin.utils.Constants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeAPI {

    @GET("search")
    suspend fun getYoutubeVideos(): Response<YoutubeVideo>

    @GET("search")
    suspend fun getYoutubeVideosList(
        @Query("relatedToVideoId") reatedVideoId: String,
        @Query("type") type: String = "video"
    ): Response<YoutubeVideoRelated>

    companion object {

        operator fun invoke(
        ): YoutubeAPI {

            val interceptor = Interceptor {
                val url = it.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.YoutubeApi)
                    .addQueryParameter("channelId", "UCt2JXOLNxqry7B_4rRZME3Q")
                    .addQueryParameter("part", "snippet,id")
                    .addQueryParameter("order", "date")
                    .addQueryParameter("maxResults", "50")
                    .build()
                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.youtubeAPI)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(YoutubeAPI::class.java)
        }
    }
}