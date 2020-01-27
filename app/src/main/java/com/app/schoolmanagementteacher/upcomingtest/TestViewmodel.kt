package com.app.schoolmanagementteacher.upcomingtest

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.response.UpcomingTestList
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

class TestViewmodel(val repository: Repository):ViewModel() {
        var testListener:TestListener?=null

    suspend fun addTest( incharge_id:String,
                           title:String,
                           date:String,
                         info:String
                          )
    {
        testListener?.onStarted()
        repository.addTest(incharge_id,title,date,info).enqueue(object :Callback<Homework>{
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: "+t.message);
                testListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if(response.isSuccessful) {
                    testListener?.onSuccess(response.body()!!)
                    Log.e("homeviewmodel", "onsuccess: "+response.body()!!.response)

                }else
                {
                    testListener?.onFailure(JSONObject(response?.errorBody()?.string()).getString("response"))
                }

            }

        })

    }


    suspend fun updateTest( id:String,
                         title:String,
                         date:String,
                         info:String
    )
    {
        testListener?.onStarted()
        repository.updateTest(id,title,date,info).enqueue(object :Callback<Homework>{
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: "+t.message);
                testListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if(response.isSuccessful) {
                    testListener?.onSuccess(response.body()!!)
                    Log.e("homeviewmodel", "onsuccess: "+response.body()!!.response)

                }else
                {
                    testListener?.onFailure(JSONObject(response?.errorBody()?.string()).getString("response"))
                }

            }

        })

    }

    fun allTest( incharge_id: String){
        testListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allTest(incharge_id).enqueue(object:Callback<UpcomingTestList>{
                override fun onFailure(call: Call<UpcomingTestList>, t: Throwable) {
                testListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<UpcomingTestList>,
                    response: Response<UpcomingTestList>
                ) {
                    if(response.isSuccessful)
                    testListener?.onAllTestSuccess(response.body()!!)
                    else
                        testListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}