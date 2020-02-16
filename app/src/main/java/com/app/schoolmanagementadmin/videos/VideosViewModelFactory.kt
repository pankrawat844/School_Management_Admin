package com.app.schoolmanagementadmin.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementadmin.network.VideoRepository

class VideosViewModelFactory(private val repository: VideoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideosViewModel(repository) as T
    }
}