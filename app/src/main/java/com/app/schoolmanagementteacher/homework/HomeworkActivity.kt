package com.app.schoolmanagementteacher.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.schoolmanagementteacher.R
import kotlinx.android.synthetic.main.activity_homework.*
import lv.chi.photopicker.PhotoPickerFragment

class HomeworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)
        camera.setOnClickListener {
            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 5,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(supportFragmentManager, "YOUR_TAG")
        }
    }
}