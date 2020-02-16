package com.app.schoolmanagementadmin.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementadmin.network.VideoRepository

class YoutubeDetailViewModelFactory(private val repository: VideoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return YoutubeDetailViewModel(
            repository
        ) as T
    }
}