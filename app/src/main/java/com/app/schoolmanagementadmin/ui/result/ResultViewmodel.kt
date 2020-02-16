package com.app.schoolmanagementadmin.ui.result

import androidx.lifecycle.ViewModel
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.network.response.Homework
import com.app.schoolmanagementadmin.network.response.StudentList
import com.app.schoolmanagementadmin.network.response.UpcomingTestList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultViewmodel(val repository: Repository) : ViewModel() {
    var testListener: ResultListener? = null

    fun allTest(incharge_id: String) {
        testListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allTest(incharge_id).enqueue(object : Callback<UpcomingTestList> {
                override fun onFailure(call: Call<UpcomingTestList>, t: Throwable) {
                    testListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<UpcomingTestList>,
                    response: Response<UpcomingTestList>
                ) {
                    if (response.isSuccessful)
                        testListener?.onAllTestSuccess(response.body()!!)
                    else
                        testListener?.onFailure(
                            JSONObject(response.errorBody()?.string()).getString(
                                "message"
                            )
                        )
//                            Log.e("error",response.errorBody()?.string())
                }

            })
        }
    }

    fun addResult(
        incharge_id: String,
        test_id: String,
        roll_no: String,
        date: String,
        max_marks: String,
        marks: String
    ) {
        testListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.addResult(incharge_id, test_id, roll_no, date, max_marks, marks)
                .enqueue(object : Callback<Homework> {
                    override fun onFailure(call: Call<Homework>, t: Throwable) {
                        testListener?.onFailure(t.message!!)
                    }

                    override fun onResponse(
                        call: Call<Homework>,
                        response: Response<Homework>
                    ) {
                        if (response.isSuccessful)
                            testListener?.onSuccess(response.body()!!)
                        else
                            testListener?.onFailure(
                                JSONObject(response.errorBody()?.string()).getString(
                                    "response"
                                )
                            )
//                            Log.e("error",response.errorBody()?.string())
                    }

                })
        }
    }


    fun allstudent(class_name: String, section_name: String) {
        testListener?.onStarted()
        CoroutineScope(Dispatchers.Main).launch {
            repository.allStudent(class_name, section_name).enqueue(object : Callback<StudentList> {
                override fun onFailure(call: Call<StudentList>, t: Throwable) {
                    testListener?.onFailure(t.message!!)
                }

                override fun onResponse(
                    call: Call<StudentList>,
                    response: Response<StudentList>
                ) {
                    if (response.isSuccessful)
                        testListener?.onAllStudentSuccess(response.body()!!)
                    else
                        testListener?.onFailure(
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