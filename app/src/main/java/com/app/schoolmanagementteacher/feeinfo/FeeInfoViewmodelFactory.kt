package com.app.schoolmanagementteacher.feeinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.schoolmanagementteacher.network.Repository

class FeeInfoViewmodelFactory(val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeeInfoViewmodel(repository) as T
    }
}