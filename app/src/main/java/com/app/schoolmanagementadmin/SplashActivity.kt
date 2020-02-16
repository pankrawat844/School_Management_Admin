package com.app.schoolmanagementadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.schoolmanagementadmin.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE)
        val handler=Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (sharedPreferences.getBoolean("islogin", false)) {

                        Intent(this@SplashActivity, HomeActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }

                } else {
                    Intent(this@SplashActivity, LoginActivity::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }
            }

        },3000)

    }
}
