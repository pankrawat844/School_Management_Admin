package com.app.schoolmanagementteacher.complaint

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.HomeworkList
import com.app.schoolmanagementteacher.response.NoticeList
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



class ComplaintViewmodel(val repository: Repository):ViewModel() {
        var complaintListener:ComplaintListener?=null

    suspend fun addNotice( incharge_id:String,
                           title:String,
                           notice:String
                          )
    {
        complaintListener?.onStarted()
        repository.addNotice(incharge_id,title,notice).enqueue(object :Callback<Homework>{
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: "+t.message);
                complaintListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                Log.e("homeviewmodel", "onsuccess: "+response.body()!!.response);
                complaintListener?.onSuccess(response.body()!!)

            }

        })

    }

    fun allNotice( incharge_id: String){
        complaintListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allNotice(incharge_id).enqueue(object:Callback<NoticeList>{
                override fun onFailure(call: Call<NoticeList>, t: Throwable) {
                complaintListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<NoticeList>,
                    response: Response<NoticeList>
                ) {
                    if(response.isSuccessful)
                    complaintListener?.onAllNoticeSuccess(response.body()!!)
                    else
                        complaintListener?.onFailure(JSONObject(response.errorBody()?.string()).getString("message"))
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

}