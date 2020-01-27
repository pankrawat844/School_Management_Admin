package com.app.schoolmanagementteacher.timetable

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.homework.HomeworkViewmodel
import com.app.schoolmanagementteacher.homework.HomeworkViewmodelFactory
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_homework.*
import kotlinx.android.synthetic.main.activity_homework.menu
import kotlinx.android.synthetic.main.activity_time_table.*
import kotlinx.android.synthetic.main.bottomsheet_homework_txt.*
import lv.chi.photopicker.PhotoPickerFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TimeTableActivity : AppCompatActivity(),KodeinAware,TimeTableListener {
    lateinit var sharedPreferences:SharedPreferences
    override val kodein by kodein()

    val factory: TimeTableViewmodelFactory by instance()
    var viewmodel: HomeworkViewmodel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        val  viewmodel = ViewModelProviders.of(this, factory).get(TimeTableViewmodel::class.java)
        viewmodel?.timetableListener = this
        sharedPreferences=getSharedPreferences("app", Context.MODE_PRIVATE)

        viewmodel?.allHomework(sharedPreferences?.getString("id","")!!)
        image.setOnClickListener {
            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = false,
                maxSelection = 5,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(supportFragmentManager, "time_table")
            menu.close(true)
        }

        pdf.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.setType("application/pdf")
                startActivityForResult(Intent.createChooser(it,"Select PDF"),0)
            }
        }

        if(sharedPreferences?.getString("role","")=="incharge")
            menu.visibility= View.VISIBLE

    }

    override fun onStarted() {

    }

    override fun onImageSuccess(data: Homework) {
    }

    override fun onPdfSuccess(data: HomeworkList) {
    }

    override fun onFailure(msg: String) {
    }
}