package com.app.schoolmanagementteacher.event

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ActivityEventBinding
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.bottomsheet_notice.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EventActivity : AppCompatActivity(), KodeinAware, EventListener {
    override val kodein by kodein()
    val factory: EventViewmodelFactory by instance()
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding =
            DataBindingUtil.setContentView<ActivityEventBinding>(this, R.layout.activity_event)
        val viewmodel = ViewModelProviders.of(this, factory).get(EventViewmodel::class.java)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.noticeListener = this
        databinding.viewmodel = viewmodel
        viewmodel.allNotice(sharedPreferences?.getString("id", "")!!)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_notice)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        menu.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottom_sheet_nxt.setOnClickListener {
            val title = titl.text.toString().trim()
            val notice = notice_txt.text.toString().trim()
            CoroutineScope(Dispatchers.Main).launch {
                if (title.isNullOrBlank() || notice.isNullOrBlank())
                    toast("Both field are mandatory.")
                else
                    viewmodel.addNotice(sharedPreferences?.getString("id", "")!!, title, notice)
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
        Log.e("TAG", "onAllNoticeSuccess: " + data.response)
        initRecyerview(data.response?.toNoticeItem()!!)
    }

    private fun initRecyerview(toNoticeItem: List<EventItem>) {
        val adapter = GroupAdapter<ViewHolder>().apply {
            addAll(toNoticeItem)
        }
        notice_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@EventActivity)
            setAdapter(adapter)


        }

    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)
    }


    private fun List<NoticeList.Response>.toNoticeItem(): List<EventItem> {
        return this.map {
            EventItem(it)
        }
    }
}