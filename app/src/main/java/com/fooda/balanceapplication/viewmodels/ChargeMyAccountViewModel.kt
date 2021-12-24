package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.buy.BuyBalanceResponse
import com.fooda.balanceapplication.models.charge.ChargeResponse
import com.fooda.balanceapplication.repository.BalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class ChargeMyAccountViewModel  @Inject constructor(
        private val balanceRepository: BalanceRepository

) : ViewModel() {
    private val _chargeAccountLiveData = MutableLiveData<ResultState<ChargeResponse>>()
    val chargeAccountLiveData: LiveData<ResultState<ChargeResponse>> get() = _chargeAccountLiveData
    fun chargeAccount(){
        //    Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
            try {
                setResultCharge(ResultState.Loading())
                val result = async() {
                    balanceRepository.chargeAccount()
                }
                showChargeHasData(result)


            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultCharge(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultCharge(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultCharge(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setResultCharge(resultState: ResultState<ChargeResponse>) {
        _chargeAccountLiveData.postValue(resultState)
    }
    private suspend fun showChargeHasData(result: Deferred<ChargeResponse>) {
        setResultCharge(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }


}