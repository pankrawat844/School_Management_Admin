package com.app.schoolmanagementteacher.home


interface HomeFragmentListener {
    fun onDataChanged(name: String)
    fun onError(msg: String)
    fun onStarted()

}