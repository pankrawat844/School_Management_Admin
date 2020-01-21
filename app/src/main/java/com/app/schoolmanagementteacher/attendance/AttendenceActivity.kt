package com.app.schoolmanagementteacher.attendance

import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.utils.toast
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kotlinx.android.synthetic.main.activity_attendence.*
import java.text.DateFormatSymbols
import java.util.*

class AttendenceActivity : AppCompatActivity() {
    private var mShortMonths: Array<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendence)

       calenderview.setOnDayClickListener(object :OnDayClickListener{
           override fun onDayClick(eventDay: EventDay) {
               toast(eventDay.calendar.time.toString())
           }

       })
    }


}