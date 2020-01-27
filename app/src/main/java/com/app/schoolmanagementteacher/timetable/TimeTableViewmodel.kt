package com.app.schoolmanagementteacher.timetable

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part

class TimeTableViewmodel(val repository: Repository):ViewModel() {
    var timetableListener: TimeTableListener?=null

    suspend fun uploadimg(@Part("incharge_id") incharge_id: RequestBody,
                          @Part("date") date: RequestBody,
                          @Part("homework_txt") homework_txt: RequestBody,
                          img: MultipartBody.Part)
    {
        timetableListener?.onStarted()
        repository.sendHomework(incharge_id,date, homework_txt, img).enqueue(object :
            Callback<Homework> {
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: "+t.message);
                timetableListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if(response.isSuccessful) {
                    Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response)
                    timetableListener?.onImageSuccess(response.body()!!)
                }else
                {
                    timetableListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("response"))
                }

            }

        })

    }

    fun allHomework( incharge_id: String){
        timetableListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allHomework(incharge_id).enqueue(object: Callback<HomeworkList> {
                override fun onFailure(call: Call<HomeworkList>, t: Throwable) {
                    timetableListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<HomeworkList>,
                    response: Response<HomeworkList>
                ) {
                    if(response.isSuccessful)
                        timetableListener?.onPdfSuccess(response.body()!!)
                    else
                        timetableListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}