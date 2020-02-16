package com.app.schoolmanagementadmin.ui.notice

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.ActivityNoticeBinding
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.NoticeList
import com.app.schoolmanagementadmin.utils.hide
import com.app.schoolmanagementadmin.utils.show
import com.app.schoolmanagementadmin.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.activity_notice.menu
import kotlinx.android.synthetic.main.activity_notice.progress_bar
import kotlinx.android.synthetic.main.bottomsheet_notice.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        databinding.viewmodel=viewmodel
        viewmodel.allNotice(sharedPreferences?.getString("id","")!!)
        val bottomSheetBehavior=BottomSheetBehavior.from(bottom_sheet_notice)
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
        menu.setOnClickListener {
            if(bottomSheetBehavior.state==BottomSheetBehavior.STATE_HIDDEN)
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
        }
        bottom_sheet_nxt.setOnClickListener {
            val title=titl.text.toString().trim()
            val notice=notice_txt.text.toString().trim()
            CoroutineScope(Dispatchers.Main).launch {
                if(title.isNullOrBlank() || notice.isNullOrBlank())
                    toast("Both field is mandatory.")
                else
                viewmodel.addNotice(sharedPreferences?.getString("id", "")!!, title,notice)
            }
        }
        if (sharedPreferences?.getString("role", "") == "incharge")
            menu.visibility = View.VISIBLE
    }

    override fun onStarted() {
    progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
        toast(data.response!!)
        finish()
    }

    override fun onAllNoticeSuccess(data: NoticeList) {
        progress_bar.hide()
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
        progress_bar.hide()
        toast(msg)
    }


    private fun List<NoticeList.Response>.toNoticeItem():List<NoticeItem>
    {
        return this.map {
            NoticeItem(it)
        }
    }
}