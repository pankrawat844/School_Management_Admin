package com.app.schoolmanagementadmin.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementadmin.ui.businfo.BusInfoViewmodel
import com.app.schoolmanagementadmin.network.Repository

class BusInfoViewmodelFactory(val repository: Repository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusInfoViewmodel(repository) as T
    }
}