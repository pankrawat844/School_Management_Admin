package com.app.schoolmanagementadmin.ui.result

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.databinding.ActivityResultBinding
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.StudentList
import com.app.schoolmanagementadmin.network.response.UpcomingTestList
import com.app.schoolmanagementadmin.upcomingtest.UpcoingTestItem
import com.app.schoolmanagementadmin.utils.RecyclerItemClickListenr
import com.app.schoolmanagementadmin.utils.hide
import com.app.schoolmanagementadmin.utils.show
import com.app.schoolmanagementadmin.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.bottomsheet_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity(), KodeinAware, ResultListener {
    override val kodein by kodein()
    val factory: ResultmodelFactory by instance()
    var sharedPreferences: SharedPreferences? = null
    lateinit var list: UpcomingTestList
    var isupdateing: Boolean = false
    var viewmodel: ResultViewmodel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding = DataBindingUtil.setContentView<ActivityResultBinding>(
            this,
            R.layout.activity_result
        )
        viewmodel = ViewModelProviders.of(this, factory).get(ResultViewmodel::class.java)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel?.testListener = this
        databinding.viewmodel = viewmodel
        viewmodel?.allTest(sharedPreferences?.getString("id", "")!!)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_result)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN





        bottom_sheet_nxt.setOnClickListener {
            val roll_no = roll_no.selectedItem.toString()
            val max = max_marks.text.toString().trim()
            val marks = marks_obtained.text.toString().trim()
            CoroutineScope(Dispatchers.Main).launch {
                if (max.isNullOrBlank() || date.text.toString().isNullOrEmpty() || marks.isNullOrEmpty())
                    toast("All fields are mandatory.")
                else {
                    viewmodel?.addResult(
                        sharedPreferences?.getString("id", "")!!,
                        id.text.toString(),
                        roll_no,
                        date.text.toString(),
                        max,
                        marks
                    )
                }
            }
        }
        date.text = SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis())
        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                date.text = sdf.format(cal.time)

            }
        date.setOnClickListener {
            DatePickerDialog(
                this@ResultActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        text_recyclerview.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                text_recyclerview,
                object : RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                            name.setText(list.response?.get(position)?.testName!!)
                            date.text = list.response?.get(position)?.date
                            id.text = list.response?.get(position)?.id
                        }

                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }

                })
        )

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
        toast(data.response!!)
        finish()
    }

    override fun onAllStudentSuccess(data: StudentList) {
        progress_bar.hide()
        roll_no.adapter = data.response?.toItem()?.let {
            ArrayAdapter(
                this, android.R.layout.simple_dropdown_item_1line,
                it
            )
        }
    }

    private fun List<StudentList.Response>.toItem(): List<String> {
        return this.map {
            it.rollNo!!
        }
    }

    override fun onAllTestSuccess(data: UpcomingTestList) {
        progress_bar.hide()
        list = data
        Log.e("TAG", "onAllNoticeSuccess: " + data.response)
        initRecyerview(data.response?.toNoticeItem()!!)
        viewmodel?.allstudent(
            sharedPreferences?.getString("class_name", "")!!,
            sharedPreferences?.getString("section_name", "")!!
        )
    }

    private fun initRecyerview(toNoticeItem: List<UpcoingTestItem>) {
        val adapter = GroupAdapter<ViewHolder>().apply {
            addAll(toNoticeItem)
        }
        text_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            setAdapter(adapter)


        }

    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)
    }


    private fun List<UpcomingTestList.Response>.toNoticeItem(): List<UpcoingTestItem> {
        return this.map {
            UpcoingTestItem(it)
        }
    }
}