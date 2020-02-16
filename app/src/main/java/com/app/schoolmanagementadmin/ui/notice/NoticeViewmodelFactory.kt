package com.app.schoolmanagementadmin.ui.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementadmin.network.Repository

class NoticeViewmodelFactory(val repository: Repository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoticeViewmodel(repository) as T
    }
}