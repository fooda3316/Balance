package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.buy.BuyBalanceResponse
import com.fooda.balanceapplication.models.buy.BuyScratchResponse
import com.fooda.balanceapplication.repository.BalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class BuyBalanceViewModel  @Inject constructor(
        private val balanceRepository: BalanceRepository

) : ViewModel() {
    private val _buyScratchLiveData = MutableLiveData<ResultState<BuyScratchResponse>>()
    val buyBalanceScratchLiveData: LiveData<ResultState<BuyScratchResponse>> get() = _buyScratchLiveData
    private val _buyBalanceLiveData = MutableLiveData<ResultState<BuyBalanceResponse>>()
    val buyBalanceBalanceLiveData: LiveData<ResultState<BuyBalanceResponse>> get() = _buyBalanceLiveData
    fun buyBalance(amount: Int,type: String,phone:String){
    //    Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
            try {
                setResultScratch(ResultState.Loading())
                val result = async() {
                    balanceRepository.buyBalance(amount,type,phone)
                }
                showBalanceHasData(result)

            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultScratch(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultScratch(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultScratch(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }
    fun buyScratch(amount: Int,type:String){
        //    Log.d(TAG, "doLogin: ")
        viewModelScope.launch {
            try {
                setResultScratch(ResultState.Loading())
                val result = async() {
                    balanceRepository.buyScratch(amount,type)
                }
                showScratchHasData(result)


            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultScratch(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultScratch(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultScratch(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setResultScratch(resultState: ResultState<BuyScratchResponse>) {
        _buyScratchLiveData.postValue(resultState)
    }
    private suspend fun showScratchHasData(result: Deferred<BuyScratchResponse>) {
        setResultScratch(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
    private fun setResultBalance(resultState: ResultState<BuyBalanceResponse>) {
        _buyBalanceLiveData.postValue(resultState)
    }
    private suspend fun showBalanceHasData(result: Deferred<BuyBalanceResponse>) {
        setResultBalance(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}