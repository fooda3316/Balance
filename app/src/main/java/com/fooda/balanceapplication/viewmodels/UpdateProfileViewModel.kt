package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.phone.ProfileResponse
import com.fooda.balanceapplication.models.phone.UpdateNumbersResponse
import com.fooda.balanceapplication.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class UpdateProfileViewModel  @Inject constructor(
        private val profileRepository: ProfileRepository

) : ViewModel() {
    private  val TAG = "LoginViewModel"
    private val _updateProfileLiveData = MutableLiveData<ResultState<UpdateNumbersResponse>>()
    val updateProfileLiveData: LiveData<ResultState<UpdateNumbersResponse>> get() = _updateProfileLiveData
    fun updateResult(zain:String,mtn:String,sudani:String){
        viewModelScope.launch (Dispatchers.Main){

            try {
                setResultOtp(ResultState.Loading())
                val result = async() {
                    profileRepository.updateProfile(zain,mtn,sudani)
                }
                showHasData(result)

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

    private fun setResultOtp(resultState: ResultState<UpdateNumbersResponse>) {
        _updateProfileLiveData.postValue(resultState)
    }
    private suspend fun showHasData(result: Deferred<UpdateNumbersResponse>) {
        setResultOtp(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}