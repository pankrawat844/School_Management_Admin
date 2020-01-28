package com.app.schoolmanagementteacher.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementteacher.businfo.BusInfoViewmodel
import com.app.schoolmanagementteacher.network.Repository

class BusInfoViewmodelFactory(val repository: Repository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusInfoViewmodel(repository) as T
    }
}