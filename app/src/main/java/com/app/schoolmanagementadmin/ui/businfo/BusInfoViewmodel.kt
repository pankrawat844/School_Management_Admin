package com.app.schoolmanagementadmin.ui.businfo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.Timetable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusInfoViewmodel(val repository: Repository):ViewModel() {
    var businfoListener: BusInfoListener? = null

    suspend fun upload(
        class_name: RequestBody,
        section_name: RequestBody,
        img: MultipartBody.Part
    ) {
        businfoListener?.onStarted()
        repository.uploadBusInfo(class_name, section_name, img).enqueue(object :
            Callback<Homework> {
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: " + t.message)
                businfoListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if (response.isSuccessful) {
                    Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response)
                    businfoListener?.onImageSuccess(response.body()!!)
                }else
                {
                    businfoListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("response"))
                }

            }

        })

    }

    fun businfo(
        class_name: String,
        section_name: String
    ) {
        businfoListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.getBusInfo(class_name, section_name).enqueue(object : Callback<Timetable> {
                override fun onFailure(call: Call<Timetable>, t: Throwable) {
                    businfoListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<Timetable>,
                    response: Response<Timetable>
                ) {
                    if (response.isSuccessful)
                        businfoListener?.onSuccess(response.body()!!)
                    else
                        businfoListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}