package com.app.schoolmanagementadmin.ui.homework

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementadmin.R
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.HomeworkList
import com.app.schoolmanagementadmin.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_homework.*
import kotlinx.android.synthetic.main.bottomsheet_homework_txt.*
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class HomeworkActivity : AppCompatActivity(), PhotoPickerFragment.Callback, KodeinAware,
    HomeworkListener {
    override val kodein by kodein()

    val factory: HomeworkViewmodelFactory by instance()
    var viewmodel: HomeworkViewmodel? = null
    var sharedPreferences: SharedPreferences? = null
    var list: List<HomeworkList.Response>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)
        viewmodel = ViewModelProviders.of(this, factory).get(HomeworkViewmodel::class.java)
        viewmodel?.homeworkListener = this
        sharedPreferences=getSharedPreferences("app", Context.MODE_PRIVATE)

        viewmodel?.allHomework(sharedPreferences?.getString("id","")!!)
        camera.setOnClickListener {
            PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 5,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(supportFragmentManager, "YOUR_TAG")
            menu.close(true)
        }
        val bottomSheetBehavior=BottomSheetBehavior.from(bottom_sheet_homework)
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
        txt.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        if (sharedPreferences?.getString("role", "") == "incharge")
            menu.visibility = View.VISIBLE


        recycler_view.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                recycler_view,
                object : RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        if (!list?.get(position)?.homeworkImg.isNullOrEmpty()) {
                            Intent(this@HomeworkActivity, FullScreenActivity::class.java).also {
                                it.putExtra("url", list?.get(position)?.homeworkImg!!)
                                startActivity(it)
                            }
                        }
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                    }

                })
        )
    }



    fun  getImageUri( inContext:Context,  inImage:Bitmap):Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun onImagesPicked(photos: ArrayList<Uri>, data: Intent?) {
        Log.e("TAG", "onImagesPicked: " + photos[0])

        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(photos[0]))
        toast("OnActivityResult" + bitmap.toString())
        val path = savebitmap(bitmap)!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData(
            "fileToUpload",
            Calendar.getInstance().time.toString(),
            path
        )
        val incharge_id: RequestBody =
            sharedPreferences?.getString("id", "")!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val date: RequestBody = "date".toRequestBody("text/plain".toMediaTypeOrNull())
        val txt: RequestBody = "txt".toRequestBody("text/plain".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.uploadimg(incharge_id, date, txt, body)
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onImagesPicked(photos: ArrayList<Uri>) {
        Log.e("TAG", "onImagesPicked: " + photos[0])
        var path: RequestBody? = null

        path = File(photos[0].path).asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val incharge_id: RequestBody =
            sharedPreferences?.getString("id", "")!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val date: RequestBody = "date".toRequestBody("text/plain".toMediaTypeOrNull())
        val txt: RequestBody = "txt".toRequestBody("text/plain".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.uploadimg(incharge_id, date, txt, body)
        }
    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Homework) {
        progress_bar.hide()
        toast(data.response!!)
    }

    override fun onAllHomeworkSuccess(data: HomeworkList) {
        Log.e("TAG", "onAllHomeworkSuccess: " + data.response.toString())
        progress_bar.hide()
        list = data.response!!
        val adapter = HomeworkRecyclerviewAdapter(data.response)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }



    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)

    }

    private fun savebitmap(bmp:Bitmap): File? {
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        var outStream:OutputStream?= null
        // String temp = null;
        var file = File(extStorageDirectory, "temp.png")
        if (file.exists())
        {
            file.delete()
            file = File(extStorageDirectory, "temp.png")
        }
        try
        {
            outStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
        }
        catch (e:Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }
}