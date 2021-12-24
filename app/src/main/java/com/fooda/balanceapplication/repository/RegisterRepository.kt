package com.fooda.balanceapplication.repository

import android.util.Log
import androidx.room.withTransaction
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.registeration.RegisterResponse
import com.fooda.balanceapplication.retrofit.ScratchAPI
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import javax.inject.Inject

class RegisterRepository@Inject constructor(
    private val api: ScratchAPI
) {
   suspend fun register(nameBody:String,
                        phoneBody:String,
                        passwordBody:String,
                        confirmBody:String,
                        typeBody:String): RegisterResponse {
       return api.register(nameBody,phoneBody,passwordBody,confirmBody,typeBody)
   }

}