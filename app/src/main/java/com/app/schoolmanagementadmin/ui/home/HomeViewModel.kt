package com.app.schoolmanagementadmin.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementadmin.WebviewActivity
import com.app.schoolmanagementadmin.ui.attendance.AttendenceActivity
import com.app.schoolmanagementadmin.ui.businfo.BusInfoActivity
import com.app.schoolmanagementadmin.ui.event.EventActivity

import com.app.schoolmanagementadmin.ui.feeinfo.FeeInfoActivity
import com.app.schoolmanagementadmin.ui.homework.HomeworkActivity
import com.app.schoolmanagementadmin.ui.leave.LeaveActivity
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.ui.notice.NoticeActivity
import com.app.schoolmanagementadmin.network.response.Classes
import com.app.schoolmanagementadmin.ui.result.ResultActivity
import com.app.schoolmanagementadmin.ui.timetable.TimeTableActivity
import com.app.schoolmanagementadmin.upcomingtest.UpcomingTest
import com.app.schoolmanagementadmin.videos.VideosActivity


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
        Intent(view.context, TimeTableActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onBusInfoClick(view: View) {
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

    fun onVideosClick(view: View) {
        Intent(view.context, VideosActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onAboutUsClick(view: View) {
        Intent(view.context, WebviewActivity::class.java).also {
            it.putExtra("url", "https://www.ndpsedu.com/aboutus.aspx")
            view.context.startActivity(it)
        }
    }

    fun onContactUsClick(view: View) {
        Intent(view.context, WebviewActivity::class.java).also {
            it.putExtra("url", "https://www.ndpsedu.com/contactus.aspx")
            view.context.startActivity(it)
        }
    }

    fun onResultClick(view: View) {
        Intent(view.context, ResultActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onEventClick(view: View) {
        Intent(view.context, EventActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}