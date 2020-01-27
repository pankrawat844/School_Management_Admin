package com.app.schoolmanagementteacher.attendance

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.databinding.ActivityStudentListBinding
import com.app.schoolmanagementteacher.response.CheckAttendence
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import kotlinx.android.synthetic.main.activity_student_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class StudentListActivity : AppCompatActivity(), KodeinAware, AttendenceListener, OnOptionSelected {


    override val kodein by kodein()
    val factory: AttendenceViewmodelFactory by instance()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    var list: List<StudentList.Response>? = arrayListOf()
    lateinit var viewmodel: AttendenceViewmodel
    var attendenceData: CheckAttendence? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityStudentListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_student_list)
        viewmodel = ViewModelProviders.of(this, factory).get(AttendenceViewmodel::class.java)
        linearLayoutManager = LinearLayoutManager(this)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.attedenceListener = this
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel.check_attendence(
                intent.getStringExtra("date"),
                sharedPreferences.getString("class_name", "")!!,
                sharedPreferences.getString("section_name", "")!!
            )
        }


        submit.setOnClickListener {
            //            for (i in list!!)
//            {
//                val holder = recycler_view.findViewHolderForAdapterPosition(i) as RecyclerView.ViewHolder
//                                Log.e("TAG", "initRecyerview: "+i.attendence )
            val jsonArray = JSONArray()
            for (j in list!!) {
                if (j.attendence != null) {
                    val jsonObject = JSONObject()
                    jsonObject.put("name", j.name)
                    jsonObject.put("roll_no", j.rollNo)
                    jsonObject.put("attendence", j.attendence)
                    jsonArray.put(jsonObject)
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                viewmodel.add_attendence(
                    intent.getStringExtra("date"),
                    sharedPreferences.getString("class_name", "")!!,
                    sharedPreferences.getString("section_name", "")!!,
                    jsonArray.toString()
                )
            }
//            }
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
        progress_bar.hide()
        finish()
        toast(data.response!!)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onAllStudentSuccess(data: StudentList) {
        progress_bar.hide()
        if (attendenceData?.response != null) {

            for (i in 0 until attendenceData?.response!!.size) {
                val jsonObject = attendenceData?.response!![i]
                for (j in data.response!!) {
                    if (jsonObject?.rollNo == j.rollNo) {
                        j.attendence = jsonObject?.attendence
                    }
                }
            }
        }

        initRecyerview(data.response!!)
    }

    override fun onCheckAttendence(data: CheckAttendence) {
        Log.e("TAG", "onCheckAttendence: "+data.toString())
        if (data.success!!) {
            attendenceData = data
        }
        viewmodel.allstudent(
            sharedPreferences.getString("class_name", "")!!,
            sharedPreferences.getString("section_name", "")!!
        )
    }

    override fun onCheckAttendenceFailour(msg: String) {
        toast(msg)
        viewmodel.allstudent(
            sharedPreferences.getString("class_name", "")!!,
            sharedPreferences.getString("section_name", "")!!
        )
    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)
    }

    private fun initRecyerview(toNoticeItem: List<StudentList.Response>) {
        list = toNoticeItem
        val adapter = StudentAdapter(toNoticeItem)
        adapter.optionSelected = this
        recycler_view.apply {
            layoutManager = linearLayoutManager
            setAdapter(adapter)

        }

        recycler_view.viewTreeObserver.addOnGlobalLayoutListener { }


    }



    private fun List<StudentList.Response>.toStudentItem(): List<StudentItem> {
        return this.map {
            StudentItem(it)
        }
    }

    override fun onOptionSelected(position: Int, itemSelected: String) {

        list?.get(position)?.attendence = itemSelected
    }
}