package com.fooda.balanceapplication.repository

import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.phone.ProfileResponse
import com.fooda.balanceapplication.models.phone.UpdateImageResponse
import com.fooda.balanceapplication.models.phone.UpdateNumbersResponse
import com.fooda.balanceapplication.retrofit.ScratchAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ScratchAPI,
    private val preferenceHelper: PreferenceHelper
) {

   suspend fun updateProfile(zain:String,mtn:String,sudani:String): UpdateNumbersResponse {

      return api.updateProfile(preferenceHelper.getUserID(),zain,mtn,sudani)
   }

    suspend fun uploadImage(
                            imageBodyPart: MultipartBody.Part): UpdateImageResponse? {
        return preferenceHelper.getUserToken()?.let { api.uploadImage(it,getMultipartBody(preferenceHelper.getUserID().toString()),imageBodyPart) }
        // return preferenceHelper.getUserToken()?.let { api.uploadImage(it,idBody,imageBodyPart) }
    }

private fun getMultipartBody(content:String):RequestBody{
    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), content)
}
}