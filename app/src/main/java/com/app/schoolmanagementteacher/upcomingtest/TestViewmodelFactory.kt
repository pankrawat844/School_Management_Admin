package com.app.schoolmanagementteacher.upcomingtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementteacher.network.Repository

class TestViewmodelFactory(val repository: Repository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TestViewmodel(repository) as T
    }
}