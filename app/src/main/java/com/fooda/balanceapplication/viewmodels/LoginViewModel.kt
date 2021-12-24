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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel  @Inject constructor(
        private val loginRepository: LoginRepository

) : ViewModel() {
    private  val TAG = "LoginViewModel"
    private val _loginLiveData = MutableLiveData<ResultState<RegisterResponse>>()
    val loginLiveData: LiveData<ResultState<RegisterResponse>> get() = _loginLiveData
    fun loginResult(phone: String){
       // lateinit var loginResult: RegisterResponse
        Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
         //   Log.d(TAG, "launch loginResult: ")
         //   _loginLiveData.postValue(loginRepository.login(phone))
            //loginResult = loginRepository.login(phone)
            try {
                setResultOtp(ResultState.Loading())
                val result = async() {
                    loginRepository.login(phone)
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
        _loginLiveData.postValue(resultState)
    }
    private suspend fun showHasData(result: Deferred<RegisterResponse>) {
        setResultOtp(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}