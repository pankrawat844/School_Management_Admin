package com.app.schoolmanagementadmin.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.app.schoolmanagement.utils.ApiException
import com.app.schoolmanagement.utils.NoInternetException
import com.app.schoolmanagementadmin.network.Repository
import com.app.schoolmanagementadmin.network.response.TeacherLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewmodel(val repository: Repository):ViewModel() {
    var teacherid:String?=null
    var password:String?=null
    var loginListener:LoginListener?=null

    fun onLoginclick(view: View) {
        loginListener?.onStarted()
        if (teacherid.isNullOrEmpty()) {
            loginListener?.onFailure("School id is Missing.")
            return
        }

        if (password.isNullOrEmpty()) {
            loginListener?.onFailure("Password is Missing.")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {


                    repository.getLogin( teacherid!!, password!!).enqueue(object :Callback<TeacherLogin>{
                        override fun onFailure(call: Call<TeacherLogin>, t: Throwable) {
                            loginListener?.onFailure(t.message!!)

                        }

                        override fun onResponse(
                            call: Call<TeacherLogin>,
                            response: Response<TeacherLogin>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.response.let {
                                    loginListener?.onSuccess(it!!)
                                    return@let
                                }
                                loginListener?.onFailure(response.body()?.message!!)
                            }else {
                                loginListener?.onFailure(
                                    JSONObject(response.errorBody()?.string()).getString(
                                        "message"
                                    )!!
                                )
                            }
                        }


                    })

            } catch (e: ApiException) {
                loginListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                loginListener?.onFailure(e.message!!)
            }
        }
    }


}