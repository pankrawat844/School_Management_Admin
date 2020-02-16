package com.app.schoolmanagementadmin.ui.complaint

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.NoticeList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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