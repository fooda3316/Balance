
package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.*
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.models.charge.ChargeHistory
import com.fooda.balanceapplication.models.charge.ChargeResponse
import com.fooda.balanceapplication.repository.ChargeOtherClintRepository
import com.fooda.balanceapplication.utilits.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class ChargeOtherClintViewModel  @Inject constructor(
    private val repository: ChargeOtherClintRepository

) : ViewModel() {
    private val _chargeOtherClintLiveData = MutableLiveData<ResultState<ChargeHistory>>()
    val chargeOtherClintLiveData: LiveData<ResultState<ChargeHistory>> get() = _chargeOtherClintLiveData
    fun chargeOtherClint(clintId: Int, amount: Int) {
        //    Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
            try {
                setResultCharge(ResultState.Loading())
                val result = async() {
                    repository.chargeOtherClint(clintId, amount)
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

    private fun setResultCharge(resultState: ResultState<ChargeHistory>) {
        _chargeOtherClintLiveData.postValue(resultState)
    }

    private suspend fun showChargeHasData(result: Deferred<ChargeHistory>) {
        setResultCharge(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}

