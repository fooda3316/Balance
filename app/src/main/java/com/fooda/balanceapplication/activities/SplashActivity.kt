package com.fooda.balanceapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.helpers.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getUserInfo()
        Handler().postDelayed({
            leaveSplashActivity()
        }, 4000)
    }

    private fun getUserInfo() {
        Log.d(TAG, "getUserInfo: image: ${preferenceHelper.getUserImage()}")
    }

    private fun leaveSplashActivity() {
        Log.e(TAG, "is First Lunch: ${preferenceHelper.isFirstLunch()}")
        val isFirstLunch=preferenceHelper.isFirstLunch()
        Log.e(TAG, "is First Lunch: $isFirstLunch")
        if (!isFirstLunch) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            preferenceHelper.saveFirstLunch()
            Log.e(TAG, "isUserLogged: ${preferenceHelper.isUserLogged()}")
            val isUserLogged=preferenceHelper.isUserLogged()

            if (!isUserLogged) {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        startActivity(Intent(applicationContext, HomeActivity::class.java))
//        else {
//         //   startActivity(Intent(applicationContext, HomeActivity::class.java))
//
//        }
    }
}