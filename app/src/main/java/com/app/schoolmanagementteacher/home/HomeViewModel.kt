package com.app.schoolmanagementteacher.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel

import com.app.schoolmanagement.utils.ApiException
import com.app.schoolmanagement.utils.NoInternetException
import com.app.schoolmanagementteacher.attendance.AttendenceActivity
import com.app.schoolmanagementteacher.homework.HomeworkActivity
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.notice.NoticeActivity
import com.app.schoolmanagementteacher.response.Classes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(val adminRepository: Repository) : ViewModel() {
    var class_name: String? = null
    var section_name: String? = null
    var total_student: String? = null

    var view1: Activity? = null
    var school_id: String? = null
    var homeFragmentListener: HomeFragmentListener? = null
    var dialog: AlertDialog? = null
    var class_list:Classes?=null
    var section_list:Classes?=null


    fun onHomeWorkClick(view:View){
        Intent(view.context,HomeworkActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onAttendanceClick(view:View){
        Intent(view.context,AttendenceActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onNoticeClick(view:View){
        Intent(view.context,NoticeActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


}