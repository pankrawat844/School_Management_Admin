package com.app.schoolmanagementteacher.attendance

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ActivityStudentListBinding
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import kotlinx.android.synthetic.main.activity_student_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class StudentListActivity : AppCompatActivity(), KodeinAware, AttendenceListener,OnOptionSelected {


    override val kodein by kodein()
    val factory: AttendenceViewmodelFactory by instance()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager:LinearLayoutManager
    var list:List<StudentList.Response>?= arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityStudentListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_student_list)
        val viewmodel = ViewModelProviders.of(this, factory).get(AttendenceViewmodel::class.java)
        linearLayoutManager=LinearLayoutManager(this)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.attedenceListener = this
        viewmodel.allstudent(
            sharedPreferences.getString("class_name", "")!!,
            sharedPreferences.getString("section_name", "")!!
        )

        submit.setOnClickListener {
//


            for (i in list!!)
            {
//                val holder = recycler_view.findViewHolderForAdapterPosition(i) as RecyclerView.ViewHolder
                                Log.e("TAG", "initRecyerview: "+i.attendence )

            }
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
    }

    override fun onAllStudentSuccess(data: StudentList) {
        progress_bar.hide()
        initRecyerview(data.response!!)
    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)
    }

    private fun initRecyerview(toNoticeItem: List<StudentList.Response>) {
        list=toNoticeItem
        val adapter =StudentAdapter(toNoticeItem)
        adapter.optionSelected=this
        recycler_view.apply {
            layoutManager = linearLayoutManager
            setAdapter(adapter)


        }
//        recycler_view.smoothScrollToPosition(adapter.getItemCount())
        recycler_view.viewTreeObserver.addOnGlobalLayoutListener { }


    }
    fun RecyclerView.setMaxViewPoolSize(maxViewTypeId: Int, maxPoolSize: Int) {
        for (i in 0..maxViewTypeId)
            recycledViewPool.setMaxRecycledViews(i, maxPoolSize)
    }

    private fun List<StudentList.Response>.toStudentItem(): List<StudentItem> {
        return this.map {
            StudentItem(it)
        }
    }

    override fun onOptionSelected(position: Int, itemSelected: String) {

        list?.get(position)?.attendence =itemSelected
    }
}