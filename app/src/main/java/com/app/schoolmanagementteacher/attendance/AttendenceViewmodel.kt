package com.app.schoolmanagementteacher.attendance

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.NoticeList
import com.app.schoolmanagementteacher.response.StudentList
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

class AttendenceViewmodel(val repository: Repository):ViewModel() {
    var attedenceListener: AttendenceListener? = null

    suspend fun uploadimg(
        @Part("incharge_id") incharge_id: RequestBody,
        @Part("date") date: RequestBody,
        @Part("homework_txt") homework_txt: RequestBody,
        img: MultipartBody.Part
    ) {
        attedenceListener?.onStarted()
        repository.sendHomework(incharge_id, date, homework_txt, img).enqueue(object :
            Callback<Homework> {
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: " + t.message);
                attedenceListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response);
                attedenceListener?.onSuccess(response.body()!!)

            }

        })

    }

    fun allstudent(class_name: String,section_name:String) {
        attedenceListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allStudent(class_name,section_name).enqueue(object : Callback<StudentList> {
                override fun onFailure(call: Call<StudentList>, t: Throwable) {
                    attedenceListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<StudentList>,
                    response: Response<StudentList>
                ) {
                    if (response.isSuccessful)
                        attedenceListener?.onAllStudentSuccess(response?.body()!!)
                    else
                        attedenceListener?.onFailure(
                            JSONObject(response.errorBody()?.string()).getString(
                                "message"
                            )
                        )
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }
}