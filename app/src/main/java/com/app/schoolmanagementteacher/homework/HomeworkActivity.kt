package com.app.schoolmanagementteacher.homework

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProviders
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.schoolmanagementteacher.R
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.utils.hide
import com.app.schoolmanagementteacher.utils.show
import com.app.schoolmanagementteacher.utils.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_homework.*
import kotlinx.android.synthetic.main.bottomsheet_homework_txt.*
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
import java.io.ByteArrayOutputStream
import java.io.File


class HomeworkActivity : AppCompatActivity(), PhotoPickerFragment.Callback, KodeinAware,
    HomeworkListener {
    override val kodein by kodein()

    val factory: HomeworkViewmodelFactory by instance()
    var viewmodel: HomeworkViewmodel? = null
    var sharedPreferences:SharedPreferences?=null
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
        if(bottomSheetBehavior.state==BottomSheetBehavior.STATE_HIDDEN)
        {
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
        }
    }

        if(sharedPreferences?.getString("role","")=="incharge")
            menu.visibility= View.VISIBLE

    }



    fun  getImageUri( inContext:Context,  inImage:Bitmap):Uri {
    val bytes =  ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    return Uri.parse(path);
}

    override fun onImagesPicked(photos: ArrayList<Uri>, data: Intent?) {
        val bitmap:Bitmap=data?.extras?.get("data") as Bitmap
        val tempuri:Uri=getImageUri(this,bitmap)
        val path = RequestBody.create(MediaType.parse("multipart/form-data"), File(getRealPathFromURI(tempuri)))
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val incharge_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences?.getString("id","")!!)
        val date: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "date")
        val txt: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "txt")
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel?.uploadimg(incharge_id!!, date, txt, body)
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onImagesPicked(photos: ArrayList<Uri>) {
        Log.e("TAG", "onImagesPicked: " + photos[0])
        var path:RequestBody?=null

       path = RequestBody.create(MediaType.parse("multipart/form-data"), File(photos[0].path))
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("fileToUpload", photos[0].toFile().name, path)
        val incharge_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences?.getString("id","")!!)
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

    override fun onAllHomeworkSuccess(data: HomeworkList) {
        Log.e("TAG", "onAllHomeworkSuccess: "+data.response.toString() )
        progress_bar.hide()
        val adapter=HomeworkRecyclerviewAdapter(data?.response!!)
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.adapter=adapter
    }



    override fun onFailure(msg: String) {
        progress_bar.hide()
        toast(msg)

    }

 fun getRealPathFromURI( uri:Uri):String{
    var path = "";
    if (contentResolver != null) {
        val  cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(idx);
            cursor.close();
        }
    }
    return path;
}}