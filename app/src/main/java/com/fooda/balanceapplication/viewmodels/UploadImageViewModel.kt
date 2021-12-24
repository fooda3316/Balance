package com.fooda.balanceapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.phone.UpdateImageResponse
import com.fooda.balanceapplication.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class UploadImageViewModel  @Inject constructor(
        private val profileRepository: ProfileRepository

) : ViewModel() {
    private  val TAG = "LoginViewModel"
    private val _imageLiveData = MutableLiveData<ResultState<UpdateImageResponse>>()
    val imageLiveData: LiveData<ResultState<UpdateImageResponse>> get() = _imageLiveData
    fun uploadImage( imageBodyPart: MultipartBody.Part){
        Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
            try {
                setResultOtp(ResultState.Loading())
                val result = async() {
                    profileRepository.uploadImage(imageBodyPart)
                }
                showHasData(result as Deferred<UpdateImageResponse>)

            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultOtp(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultOtp(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultOtp(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setResultOtp(resultState: ResultState<UpdateImageResponse>) {
        _imageLiveData.postValue(resultState)
    }
    private suspend fun showHasData(result: Deferred<UpdateImageResponse>) {
        setResultOtp(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}