package com.app.schoolmanagementteacher.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.attendance.AttendenceActivity
import com.app.schoolmanagementteacher.businfo.BusInfoActivity
import com.app.schoolmanagementteacher.feeinfo.FeeInfoActivity
import com.app.schoolmanagementteacher.homework.HomeworkActivity
import com.app.schoolmanagementteacher.leave.LeaveActivity
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.notice.NoticeActivity
import com.app.schoolmanagementteacher.response.Classes
import com.app.schoolmanagementteacher.upcomingtest.UpcomingTest


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

    fun onUpcoingTestClick(view: View) {
        Intent(view.context, UpcomingTest::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onTimetableClick(view: View) {
        Intent(view.context, BusInfoActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLeaveClick(view: View) {
        Intent(view.context, LeaveActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onFeeInfoClick(view: View) {
        Intent(view.context, FeeInfoActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}