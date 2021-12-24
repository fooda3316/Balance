package com.fooda.balanceapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.registeration.RegisterResponse
import com.fooda.balanceapplication.repository.LoginRepository
import com.fooda.balanceapplication.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel  @Inject constructor(
        private val registerRepository: RegisterRepository

) : ViewModel() {
     private val _registerLiveData = MutableLiveData<ResultState<RegisterResponse>>()
    val registerLiveData: LiveData <ResultState<RegisterResponse>>get() = _registerLiveData
    fun  register(nameBody:String,phoneBody:String,passwordBody:String,confirmBody:String,typeBody:String){
        viewModelScope.launch {
            //   Log.d(TAG, "launch loginResult: ")
            //   _loginLiveData.postValue(loginRepository.login(phone))
            //loginResult = loginRepository.login(phone)
            try {
                setResultOtp(ResultState.Loading())
                val result = async() {
                    registerRepository.register(nameBody,phoneBody,passwordBody,confirmBody,typeBody)
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
    private fun setResultOtp(resultState: ResultState<RegisterResponse>) {
        _registerLiveData.postValue(resultState)
    }
    private suspend fun showHasData(result: Deferred<RegisterResponse>) {
        setResultOtp(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}


