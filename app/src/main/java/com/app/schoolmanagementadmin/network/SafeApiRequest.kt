package com.app.schoolmanagement.students.network

import com.app.schoolmanagement.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val resposne = call.invoke()
        if (resposne.isSuccessful) {
            return resposne.body()!!
        } else {
            val error = resposne.errorBody()?.string()
            val errormsg = StringBuilder()
            error.let {
                try {
                    errormsg.append(JSONObject(it!!).getString("message"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    errormsg.append("\n")
                }
                errormsg.append("Error code ${resposne.code()}")
                throw ApiException(errormsg.toString())
            }
        }
    }
}