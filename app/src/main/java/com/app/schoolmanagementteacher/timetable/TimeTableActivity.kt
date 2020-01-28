package com.app.schoolmanagementteacher.timetable

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
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
import java.io.*

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
        if (data.response?.isPdf=="1")
        {
            pdfViewer.webViewClient= WebViewClient()
            pdfViewer.settings.javaScriptEnabled=true
            pdfViewer.loadUrl("http://docs.google.com/gview?embedded=true&url="+Constants.base_url+data.response.pdfPath)
            pdfViewer.visibility=View.VISIBLE
        }else if(data.response?.isPdf=="0")
        {
            Picasso.get().load(Constants.base_url+data.response.imgPath).fit().into(imageView)
            imageView.visibility=View.VISIBLE
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
        if(requestCode==0) {
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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun getUriRealPath(ctx:Context, uri:Uri):String {
        var ret = ""
        if (isAboveKitKat())
        {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri)
        }
        else
        {
            // Android OS below sdk version 19
            ret = getImageRealPath(contentResolver, uri, "")
        }
        return ret
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun getUriRealPathAboveKitkat(ctx:Context, uri:Uri):String {
        var ret = ""
        if (ctx != null && uri != null)
        {
            if (isContentUri(uri))
            {
                if (isGooglePhotoDoc(uri.authority!!)) {
                    ret = uri.lastPathSegment!!
                } else {
                    ret = getImageRealPath(contentResolver, uri, "")
                }
            }
            else if (isFileUri(uri))
            {
                ret = uri.path!!
            }
            else if (isDocumentUri(ctx, uri))
            {
                // Get uri related document id.
                val documentId = DocumentsContract.getDocumentId(uri)
                // Get uri authority.
                val uriAuthority = uri.authority
                if (isMediaDoc(uriAuthority!!))
                {
                    val idArr = documentId.split((":").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    if (idArr.size == 2)
                    {
                        // First item is document type.
                        val docType = idArr[0]
                        // Second item is document real id.
                        val realDocId = idArr[1]
                        // Get content uri by document type.
                        var mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        if ("image" == docType)
                        {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        else if ("video" == docType)
                        {
                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        else if ("audio" == docType)
                        {
                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                        // Get where clause with real document id.
                        val whereClause = MediaStore.Images.Media._ID + " = " + realDocId
                        ret = getImageRealPath(contentResolver, mediaContentUri, whereClause)
                    }
                }
                else if (isDownloadDoc(uriAuthority))
                {
                    // Build download uri.
                    val downloadUri = Uri.parse("content://downloads/public_downloads")
                    // Append download document id at uri end.
                    val downloadUriAppendId = ContentUris.withAppendedId(downloadUri, java.lang.Long.valueOf(documentId))
                    ret = getImageRealPath(contentResolver, downloadUriAppendId, "")
                }
                else if (isExternalStoreDoc(uriAuthority))
                {
                    val idArr = documentId.split((":").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    if (idArr.size == 2)
                    {
                        val type = idArr[0]
                        val realDocId = idArr[1]
                        if ("primary".equals(type, ignoreCase = true))
                        {
                            ret = Environment.getRootDirectory().path+ "/" + realDocId
                        }
                    }
                }
            }
        }
        return ret
    }

    private fun isAboveKitKat():Boolean {
        var ret = false
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        return ret
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun isDocumentUri(ctx:Context, uri:Uri):Boolean {
        var ret = false
        if (ctx != null && uri != null)
        {
            ret = DocumentsContract.isDocumentUri(ctx, uri)
        }
        return ret
    }

    private fun isContentUri(uri:Uri):Boolean {
        var ret = false
        if (uri != null)
        {
            val uriSchema = uri.scheme
            if ("content".equals(uriSchema, ignoreCase = true))
            {
                ret = true
            }
        }
        return ret
    }


    private fun isExternalStoreDoc(uriAuthority:String):Boolean {
        var ret = false
        if ("com.android.externalstorage.documents" == uriAuthority)
        {
            ret = true
        }
        return ret
    }
    /* Check whether this document is provided by DownloadsProvider. */
    private fun isDownloadDoc(uriAuthority:String):Boolean {
        var ret = false
        if ("com.android.providers.downloads.documents" == uriAuthority)
        {
            ret = true
        }
        return ret
    }
    /* Check whether this document is provided by MediaProvider. */
    private fun isMediaDoc(uriAuthority:String):Boolean {
        var ret = false
        if ("com.android.providers.media.documents" == uriAuthority)
        {
            ret = true
        }
        return ret
    }
    /* Check whether this document is provided by google photos. */
    private fun isGooglePhotoDoc(uriAuthority:String):Boolean {
        var ret = false
        if ("com.google.android.apps.photos.content" == uriAuthority)
        {
            ret = true
        }
        return ret
    }

    private fun getImageRealPath(contentResolver:ContentResolver, uri:Uri, whereClause:String):String {
        var ret = ""
        // Query the uri with condition.
        val cursor = contentResolver.query(uri, null, whereClause, null, null)
        if (cursor != null)
        {
            val moveToFirst = cursor.moveToFirst()
            if (moveToFirst)
            {
                // Get columns name by uri type.
                var columnName = MediaStore.Images.Media.DATA
                if (uri === MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                {
                    columnName = MediaStore.Images.Media.DATA
                }
                else if (uri === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                {
                    columnName = MediaStore.Audio.Media.DATA
                }
                else if (uri === MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                {
                    columnName = MediaStore.Video.Media.DATA
                }
                // Get column index.
                val imageColumnIndex = cursor.getColumnIndex(columnName)
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex)
            }
        }
        return ret
    }

    private fun isFileUri(uri:Uri):Boolean {
        var ret = false
        if (uri != null) {
            val uriSchema = uri.scheme
            if ("file".equals(uriSchema, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index: Int = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)!!
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }


    fun writeFile(n: InputStream, file: File) {
        var out: OutputStream? = null
        try {
            out = FileOutputStream(file)
            val buf = ByteArray(1024)
            val len: Int = n.read(buf)
            while (len > 0) {
                out.write(buf, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (out != null) {
                    out.close()
                }
                n.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}