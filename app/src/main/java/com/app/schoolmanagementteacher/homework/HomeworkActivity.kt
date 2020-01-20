package com.app.schoolmanagementteacher.homework

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProviders
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import kotlinx.android.synthetic.main.activity_homework.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lv.chi.photopicker.PhotoPickerFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeworkActivity : AppCompatActivity(), PhotoPickerFragment.Callback, KodeinAware,
    HomeworkListener {
    override val kodein by kodein()

    val factory: HomeworkViewmodelFactory by instance()
    var viewmodel: HomeworkViewmodel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)
        viewmodel = ViewModelProviders.of(this, factory).get(HomeworkViewmodel::class.java)
        viewmodel?.homeworkListener = this
        camera.setOnClickListener {
            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 5,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(supportFragmentManager, "YOUR_TAG")
            menu.close(true)
        }


    }

    override fun onImagesPicked(photos: ArrayList<Uri>) {
        Log.e("TAG", "onImagesPicked: " + photos[0].path)
        val path: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photos[0].path)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val incharge_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "1")
        val date: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "date")
        val txt: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "txt")
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.uploadimg(incharge_id!!, date, txt, body)
        }
    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
        progress_bar.hide()
        toast(data.response!!)
    }

    override fun onAllHomeworkSuccess(data: Homework) {

    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)

    }


}