package com.fooda.balanceapplication.repository

import android.util.Log
import androidx.room.withTransaction
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.registeration.RegisterResponse
import com.fooda.balanceapplication.retrofit.ScratchAPI
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginRepository@Inject constructor(
    private val api: ScratchAPI
) {
    private  val TAG = "LoginRepository"
   /* suspend fun login(phone: String)= loginBoundResource(fetch = {
        api.login(phone,phone)
    },
        saveFetchResult = { loginResponse ->
           if (!loginResponse.error){
           preferenceHelper.saveIsLoggedIn(loginResponse.user.image,loginResponse.token)
           }
        })*/
   suspend fun login(phone: String): RegisterResponse {
       Log.d(TAG, "login: ")
       return api.login(phone,phone)
   }

}