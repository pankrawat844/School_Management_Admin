package com.app.schoolmanagementteacher.timetable

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProviders
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import kotlinx.android.synthetic.main.activity_time_table.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lv.chi.photopicker.PhotoPickerFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class TimeTableActivity : AppCompatActivity(), KodeinAware, TimeTableListener,
    PhotoPickerFragment.Callback {
    lateinit var sharedPreferences: SharedPreferences
    override val kodein by kodein()

    val factory: TimeTableViewmodelFactory by instance()
    var viewmodel: TimeTableViewmodel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        viewmodel = ViewModelProviders.of(this, factory).get(TimeTableViewmodel::class.java)
        viewmodel?.timetableListener = this
        sharedPreferences=getSharedPreferences("app", Context.MODE_PRIVATE)

//        viewmodel?.allHomework(sharedPreferences?.getString("id","")!!)
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
                it.type = "application/pdf"
                startActivityForResult(Intent.createChooser(it,"Select PDF"),0)
            }
        }

        if (sharedPreferences.getString("role", "") == "incharge")
            menu.visibility = View.VISIBLE

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onImageSuccess(data: Homework) {
        progress_bar.hide()
        toast(data.response!!)
    }

    override fun onPdfSuccess(data: HomeworkList) {
    }

    override fun onFailure(msg: String) {
        toast(msg)
    }

    override fun onImagesPicked(photos: ArrayList<Uri>) {
        var path: RequestBody? = null
        path = File(photos[0].path).asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val class_name: RequestBody =
            sharedPreferences.getString(
                "class_name",
                ""
            )!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val section_name: RequestBody =
            sharedPreferences.getString(
                "section_name",
                ""
            )!!.toRequestBody("text/plain".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.upload(class_name, section_name, body)
        }
    }

    override fun onImagesPicked(photos: ArrayList<Uri>, data: Intent?) {
        var path: RequestBody? = null
        path = File(photos[0].path).asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val class_name: RequestBody =
            sharedPreferences.getString(
                "class_name",
                ""
            )!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val section_name: RequestBody =
            sharedPreferences.getString(
                "section_name",
                ""
            )!!.toRequestBody("text/plain".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.upload(class_name, section_name, body)
        }
    }
}