package com.app.schoolmanagementadmin.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.schoolmanagement.students.network.SafeApiRequest
import com.app.schoolmanagementadmin.network.response.YoutubeVideo
import com.app.schoolmanagementadmin.network.response.YoutubeVideoRelated

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoRepository(val api: YoutubeAPI) : SafeApiRequest() {
    val videolist = MutableLiveData<YoutubeVideo>()
    val videoRelatedlist = MutableLiveData<YoutubeVideoRelated>()


    suspend fun getVideos(): LiveData<YoutubeVideo> {
        CoroutineScope(Dispatchers.Main).launch {
            val apiRequest = api.getYoutubeVideos()
            videolist.value = apiRequest.body()
        }
        return videolist

    }

    suspend fun getRelatedVideos(videoid: String): LiveData<YoutubeVideoRelated> {
        CoroutineScope(Dispatchers.Main).launch {
            val apiRequest = api.getYoutubeVideosList(videoid)
            videoRelatedlist.value = apiRequest.body()
        }
        return videoRelatedlist

    }
}