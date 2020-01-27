package com.app.schoolmanagementteacher.attendance

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.schoolmanagementteacher.network.Repository
import com.app.schoolmanagementteacher.response.CheckAttendence
import com.app.schoolmanagementteacher.response.Homework
import com.app.schoolmanagementteacher.response.StudentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendenceViewmodel(val repository: Repository):ViewModel() {
    var attedenceListener: AttendenceListener? = null

    suspend fun add_attendence(
        date: String,
        class_name: String,
        section_name: String,
        attendence: String
    ) {
        attedenceListener?.onStarted()
        repository.addAttendence(class_name, section_name, date, attendence).enqueue(object :
            Callback<Homework> {
            override fun onFailure(call: Call<Homework>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: " + t.message)
                attedenceListener?.onFailure(t.message!!)

            }

            override fun onResponse(call: Call<Homework>, response: Response<Homework>) {
                if (response.isSuccessful) {
                    Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response)
                    attedenceListener?.onSuccess(response.body()!!)
                } else {
                    attedenceListener?.onFailure(
                        JSONObject(response.errorBody()?.string()).getString(
                            "response"
                        )
                    )

                }
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
                        attedenceListener?.onAllStudentSuccess(response.body()!!)
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


    suspend fun check_attendence(
        date: String,
        class_name: String,
        section_name: String
    ) {
        attedenceListener?.onStarted()
        repository.checkAttendence(class_name, section_name, date).enqueue(object :
            Callback<CheckAttendence> {
            override fun onFailure(call: Call<CheckAttendence>, t: Throwable) {
                Log.e("homeviewmodel", "onFailure: " + t.message)
                attedenceListener?.onCheckAttendenceFailour(t.message!!)

            }

            override fun onResponse(
                call: Call<CheckAttendence>,
                response: Response<CheckAttendence>
            ) {
                if (response.isSuccessful) {
                    Log.e("homeviewmodel", "onsuccess: " + response.body()!!.response)
                    attedenceListener?.onCheckAttendence(response.body()!!)
                } else {
                    attedenceListener?.onCheckAttendenceFailour(
                        JSONObject(response.errorBody()?.string()).getString(
                            "message"
                        )
                    )

                }
            }

        })

    }
}