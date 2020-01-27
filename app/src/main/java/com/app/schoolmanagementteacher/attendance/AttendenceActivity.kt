package com.app.schoolmanagementteacher.attendance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.schoolmanagementteacher.R
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kotlinx.android.synthetic.main.activity_attendence.*
import java.util.*

class AttendenceActivity : AppCompatActivity() {
    private var mShortMonths: Array<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendence)

       calenderview.setOnDayClickListener(object :OnDayClickListener{
           override fun onDayClick(eventDay: EventDay) {
//               toast(eventDay.calendar.time.toString())
           Intent(this@AttendenceActivity,StudentListActivity::class.java).also {
               val date = Calendar.getInstance()
               date.time = eventDay.calendar.time
               it.putExtra(
                   "date",
                   "" + date.get(Calendar.DATE) + "/" + date.get(Calendar.MONTH).plus(1 )+ "/" + date.get(
                       Calendar.YEAR
                   )
               )
               startActivity(it)
           }
           }

       })
    }


}