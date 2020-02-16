package com.app.schoolmanagementadmin.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementadmin.network.VideoRepository
import com.app.schoolmanagementadmin.network.response.YoutubeVideo
import com.app.schoolmanagementadmin.utils.lazyDeferred


class VideosViewModel(private val repository: VideoRepository) : ViewModel() {
    var video_id: MutableLiveData<String>? = MutableLiveData()
    lateinit var response: LiveData<YoutubeVideo>
    val getVideolist by lazyDeferred { repository.getVideos() }


    fun setVideoId(id: String) {
        video_id?.value = id
    }


}