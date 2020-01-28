package com.app.schoolmanagementteacher.videos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.VideoRepository
import com.app.schoolmanagementteacher.utils.lazyDeferred

class YoutubeDetailViewModel(repository: VideoRepository) : ViewModel() {
    var video_id: MutableLiveData<String>? = MutableLiveData()
    val response by lazyDeferred { repository.getRelatedVideos(video_id?.value!!) }

//    fun getRelatedVideolist(video:String):LiveData<YoutubeVideoRelated>
//    {
//        response=MutableLiveData<YoutubeVideoRelated>()
////        Corotuines.main {
//        response : by lazyDeferred{repository.getRelatedVideos(video)}
////            Log.e("youtubedetailviewmodel",response.value.toString())
////        delay(5000)
////        }
//        return response
//
//    }

    fun setVideoId(id: String) {
        video_id?.value = id
    }


}