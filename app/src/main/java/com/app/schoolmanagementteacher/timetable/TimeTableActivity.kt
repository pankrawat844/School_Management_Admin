package com.app.schoolmanagementteacher.timetable

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProviders
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.Timetable
import com.app.schoolmanagementteacher.utils.Constants
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
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
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)

        viewmodel?.timetable(
            sharedPreferences.getString("class_name", "")!!,
            sharedPreferences.getString("section_name", "")!!
        )
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
            //            Intent(Intent.ACTION_GET_CONTENT).also {
//                it.type = "application/pdf"
//                startActivityForResult(Intent.createChooser(it,"Select PDF"),0)
//            }
            Dexter.withActivity(this).withPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.areAllPermissionsGranted()!!) {
                            Intent(this@TimeTableActivity, FilePickerActivity::class.java).also {
                                it.putExtra(
                                    FilePickerActivity.CONFIGS, Configurations.Builder()
                                        .setCheckPermission(true)
                                        .setShowImages(false)
                                        .setShowVideos(false)
                                        .enableImageCapture(false)
                                        .setMaxSelection(1)
                                        .setSuffixes("pdf")
                                        .setShowFiles(true)
                                        .setSkipZeroSizeFiles(true)
                                        .build()
                                )
                                startActivityForResult(it, 0)
                            }
                            menu.close(true)
                        } else {

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }

                }).check()


        }

//        if (sharedPreferences.getString("role", "") == "incharge")
//            menu.visibility = View.VISIBLE

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onImageSuccess(data: Homework) {
        progress_bar.hide()
        toast(data.response!!)
        finish()
    }

    override fun onPdfSuccess(data: Homework) {
        progress_bar.hide()
        toast(data.response!!)
        finish()
    }

    override fun onSuccess(data: Timetable) {
        progress_bar.hide()
        if (data.response?.isPdf == "1") {
            pdfViewer.webViewClient = WebViewClient()
            pdfViewer.settings.javaScriptEnabled = true
            pdfViewer.loadUrl("http://docs.google.com/gview?embedded=true&url=" + Constants.base_url + data.response.pdfPath)
            pdfViewer.visibility = View.VISIBLE
        } else if (data.response?.isPdf == "0") {
            Picasso.get().load(Constants.base_url + data.response.imgPath).fit().into(imageView)
            imageView.visibility = View.VISIBLE
        }
    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                var path: RequestBody? = null
                var files: ArrayList<MediaFile> =
                    data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)!!

                Log.e("TAG", "onActivityResult: " + files[0].uri.path)
                Log.e("TAG", "onActivityResult: " + files[0].path)

                path = File(files[0].path).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("fileToUpload", "xz", path)
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
    }


}