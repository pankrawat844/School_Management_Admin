 package lv.chi.photopicker.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.schoolmanagementadmin.utils.toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


internal class CameraActivity : AppCompatActivity() {

    private lateinit var output: Uri

    private var permissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 23) {
            permissionGranted = hasCameraPermission()
        } else permissionGranted = true

        if (savedInstanceState == null) {
//            val image =
//                File(appFolderCheckandCreate(), "img" + getTimeStamp() + ".jpg")
//            output = Uri.fromFile(image)
//            output = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider",image);
            output=provideImageUri()
            if (permissionGranted) requestImageCapture()
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    Request.CAMERA_ACCESS_PERMISSION
                )
            }
        } else savedInstanceState.getParcelable<Uri>(Key.OUTPUT)?.let {
            output = it
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Request.IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, Intent().setData(output))
            } else {
                contentResolver.delete(output, null, null)
            }
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: kotlin.IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Request.CAMERA_ACCESS_PERMISSION && hasCameraPermission()) {
            permissionGranted = true
            requestImageCapture()
        } else onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Key.OUTPUT, output)
    }

    private fun provideImageUri() = createTempFile(
        suffix = ".jpg",
        directory = File(this.cacheDir, "camera").apply { mkdirs() }
    )
        .apply { deleteOnExit() }
        .providerUri(this)

    private fun requestImageCapture() =
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, output)
                .also { intent ->
                    grantUriPermission(
                        intent.resolveActivity(packageManager).packageName,
                        output,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                },
            Request.IMAGE_CAPTURE
        )

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private object Request {
        const val IMAGE_CAPTURE = 1
        const val CAMERA_ACCESS_PERMISSION = 2
    }

    private object Key {
        const val OUTPUT = "output"
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, CameraActivity::class.java)
    }


    private fun appFolderCheckandCreate(): String? {
        var appFolderPath = ""
        val externalStorage: File = Environment.getExternalStorageDirectory()
        if (externalStorage.canWrite()) {
            appFolderPath = externalStorage.absolutePath + "/SchoolManagementTeacher"
            val dir = File(appFolderPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
        } else {
            toast("  Storage media not found or is full ! ")
        }
        return appFolderPath
    }


    private fun getTimeStamp(): String? {
        val timestamp: Long = Date().getTime()
        val cal: Calendar = Calendar.getInstance()
        cal.setTimeInMillis(timestamp)
        return SimpleDateFormat("HH_mm_ss_SSS").format(cal.getTime())
    }
}