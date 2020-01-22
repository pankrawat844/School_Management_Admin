package com.app.schoolmanagementteacher.notice

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ActivityNoticeBinding
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.bottomsheet_notice.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NoticeActivity : AppCompatActivity(),KodeinAware,NoticeListener {
    override val kodein by kodein()
    val factory:NoticeViewmodelFactory by instance()
    var sharedPreferences:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding=DataBindingUtil.setContentView<ActivityNoticeBinding>(this,R.layout.activity_notice)
        val viewmodel=ViewModelProviders.of(this,factory).get(NoticeViewmodel::class.java)
        sharedPreferences=getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.noticeListener=this
        viewmodel.allNotice(sharedPreferences?.getString("id","")!!)
        val bottomSheetBehavior=BottomSheetBehavior.from(bottom_sheet_notice)
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
        menu.setOnClickListener {
            if(bottomSheetBehavior.state==BottomSheetBehavior.STATE_HIDDEN)
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onStarted() {

    }

    override fun onSuccess(data: Homework) {
    }

    override fun onAllNoticeSuccess(data: NoticeList) {
        Log.e("TAG", "onAllNoticeSuccess: "+data.response);
        initRecyerview(data!!.response?.toNoticeItem()!!)
    }

    private fun initRecyerview(toNoticeItem: List<NoticeItem>) {
        val adapter =GroupAdapter<ViewHolder>().apply {
            addAll(toNoticeItem)
        }
        notice_recyclerview.apply {
            layoutManager=LinearLayoutManager(this@NoticeActivity)
            setAdapter(adapter)


        }

    }

    override fun onFailure(msg: String) {
        toast(msg)
    }


    private fun List<NoticeList.Response>.toNoticeItem():List<NoticeItem>
    {
        return this.map {
            NoticeItem(it)
        }
    }
}