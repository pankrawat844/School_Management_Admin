package com.app.schoolmanagementadmin.ui.home


interface HomeFragmentListener {
    fun onDataChanged(name: String)
    fun onError(msg: String)
    fun onStarted()

}