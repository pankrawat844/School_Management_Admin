package com.app.schoolmanagementteacher.leave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementteacher.network.Repository

class LeaveViewmodelFactory(val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LeaveViewmodel(repository) as T
    }
}