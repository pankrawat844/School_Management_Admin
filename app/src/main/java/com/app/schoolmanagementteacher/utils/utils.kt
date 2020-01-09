package com.app.schoolmanagementteacher.utils

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.widget.ProgressBar
import android.widget.Toast

fun ProgressBar.show(){
   visibility= View.VISIBLE
}

fun ProgressBar.hide()
{
 visibility=GONE
}

fun  Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}