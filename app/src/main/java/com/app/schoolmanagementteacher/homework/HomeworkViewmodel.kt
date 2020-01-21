package com.app.schoolmanagementteacher.homework

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

//import com.github.dhaval2404.imagepicker.ImagePicker

class HomeworkViewmodel(val repository: Repository):ViewModel() {
        var homeworkListener:HomeworkListener?=null

    suspend fun uploadimg( @Part("incharge_id") incharge_id:RequestBody,
                           @Part("date") date:RequestBody,
                           @Part("homework_txt") homework_txt:RequestBody,
                            img:MultipartBody.Part)
    {
        homeworkListener?.onStarted()
        repository.sendHomework(incharge_id,date, homework_txt, img).enqueue(object :Callback<Homework>{
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: "+t.message);
                homeworkListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                Log.e("homeviewmodel", "onsuccess: "+response.body()!!.response);
                homeworkListener?.onSuccess(response.body()!!)

            }

        })

    }

    fun allHomework( incharge_id: String){
        homeworkListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allHomework(incharge_id).enqueue(object:Callback<HomeworkList>{
                override fun onFailure(call: Call<HomeworkList>, t: Throwable) {
                homeworkListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<HomeworkList>,
                    response: Response<HomeworkList>
                ) {
                    if(response.isSuccessful)
                    homeworkListener?.onAllHomeworkSuccess(response.body()!!)
                    else
                        homeworkListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}