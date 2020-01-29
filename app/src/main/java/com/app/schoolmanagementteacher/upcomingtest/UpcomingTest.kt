package com.app.schoolmanagementteacher.upcomingtest

import android.app.DatePickerDialog
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
import com.app.schoolmanagementteacher.databinding.ActivityUpcomingTestBinding
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.UpcomingTestList
import com.app.schoolmanagementteacher.utils.RecyclerItemClickListenr
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_upcoming_test.*
import kotlinx.android.synthetic.main.bottomsheet_test.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class UpcomingTest : AppCompatActivity(), KodeinAware, TestListener {
    override val kodein by kodein()
    val factory: TestViewmodelFactory by instance()
    var sharedPreferences: SharedPreferences? = null
    lateinit var list: UpcomingTestList
    var isupdateing: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding = DataBindingUtil.setContentView<ActivityUpcomingTestBinding>(
            this,
            R.layout.activity_upcoming_test
        )
        val viewmodel = ViewModelProviders.of(this, factory).get(TestViewmodel::class.java)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        viewmodel.testListener = this
        databinding.viewmodel = viewmodel
        viewmodel.allTest(sharedPreferences?.getString("id", "")!!)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_test)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        menu.setOnClickListener {
            isupdateing = false
            add_notice.text = "Add Test"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                name.setText("")
                date.text = ""
                info.setText("")
            }

        }
        bottom_sheet_nxt.setOnClickListener {
            val name = name.text.toString().trim()
            val info = info.text.toString().trim()
            CoroutineScope(Dispatchers.Main).launch {
                if (title.isNullOrBlank() || info.isNullOrBlank() || date.text.toString().isNullOrEmpty())
                    toast("All fields are mandatory.")
                else {
                    if(isupdateing)
                        viewmodel.updateTest(
                           id.text.toString(),
                            name,
                            date.text.toString(),
                            info
                        )
                        else

                    viewmodel.addTest(
                        sharedPreferences?.getString("id", "")!!,
                        name,
                        date.text.toString(),
                        info
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
                this@UpcomingTest, dateSetListener,
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
                            isupdateing = true
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            add_notice.text = "Update Test"
                            name.setText(list.response?.get(position)?.testName!!)
                            date.text = list.response?.get(position)?.date
                            info.setText(list.response?.get(position)?.info)
                            id.text = list.response?.get(position)?.id
                        }

                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }

                })
        )
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

    override fun onAllTestSuccess(data: UpcomingTestList) {
        progress_bar.hide()
        list = data
        Log.e("TAG", "onAllNoticeSuccess: " + data.response)
        initRecyerview(data.response?.toNoticeItem()!!)
    }

    private fun initRecyerview(toNoticeItem: List<UpcoingTestItem>) {
        val adapter = GroupAdapter<ViewHolder>().apply {
            addAll(toNoticeItem)
        }
        text_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@UpcomingTest)
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