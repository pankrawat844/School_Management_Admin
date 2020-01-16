package com.app.schoolmanagementteacher.home

import com.app.schoolmanagement.admin.network.response.Classes

interface HomeFragmentListener {
    fun onDataChanged(name: String)
    fun onError(msg: String)
    fun onStarted()

}