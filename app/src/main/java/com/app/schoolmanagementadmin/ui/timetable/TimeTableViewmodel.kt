package com.app.schoolmanagementadmin.ui.timetable

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

class TimeTableViewmodel(val repository: Repository):ViewModel() {
    var timetableListener: TimeTableListener? = null

    suspend fun upload(
        class_name: RequestBody,
        section_name: RequestBody,
        img: MultipartBody.Part
    ) {
        timetableListener?.onStarted()
        repository.uploadTimetable(class_name, section_name, img).enqueue(object :
            Callback<Homework> {
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: " + t.message)
                timetableListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if (response.isSuccessful) {
                    Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response)
                    timetableListener?.onImageSuccess(response.body()!!)
                }else
                {
                    timetableListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("response"))
                }

            }

        })

    }

    fun timetable( class_name: String,
                   section_name: String){
        timetableListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.getTimetable(class_name,section_name).enqueue(object: Callback<Timetable> {
                override fun onFailure(call: Call<Timetable>, t: Throwable) {
                    timetableListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<Timetable>,
                    response: Response<Timetable>
                ) {
                    if(response.isSuccessful)
                        timetableListener?.onSuccess(response.body()!!)
                    else
                        timetableListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}