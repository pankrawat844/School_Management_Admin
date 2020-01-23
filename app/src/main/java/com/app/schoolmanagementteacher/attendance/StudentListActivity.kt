package com.app.schoolmanagementteacher.attendance

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ActivityStudentListBinding
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_student_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class StudentListActivity : AppCompatActivity(), KodeinAware, AttendenceListener {

    override val kodein by kodein()
    val factory: AttendenceViewmodelFactory by instance()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityStudentListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_student_list)
        val viewmodel = ViewModelProviders.of(this, factory).get(AttendenceViewmodel::class.java)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.attedenceListener = this
        viewmodel.allstudent(
            sharedPreferences.getString("class_name", "")!!,
            sharedPreferences.getString("section_name", "")!!
        )

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
        val adapter =StudentAdapter(toNoticeItem)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@StudentListActivity)
            setAdapter(adapter)


        }

    }

    private fun List<StudentList.Response>.toStudentItem(): List<StudentItem> {
        return this.map {
            StudentItem(it)
        }
    }
}