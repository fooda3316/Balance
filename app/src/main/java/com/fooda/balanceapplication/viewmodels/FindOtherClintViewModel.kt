
package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.*
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.models.registeration.FindClintResponse
import com.fooda.balanceapplication.repository.ChargeOtherClintRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@HiltViewModel
class FindOtherClintViewModel  @Inject constructor(
    private val repository: ChargeOtherClintRepository

) : ViewModel() {
    private val _findOtherClintLiveData = MutableLiveData<ResultState<FindClintResponse>>()
    val findOtherClintLiveData: LiveData<ResultState<FindClintResponse>> get() = _findOtherClintLiveData
//    init {
//       findOtherClint()
//    }
     fun findOtherClint(phone:String) {
        viewModelScope.launch {
            try {
                setResultFind(ResultState.Loading())
                val result = async() {
                    repository.lookForClint(phone)
                }
                showFindHasData(result)


            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultFind(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultFind(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultFind(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setResultFind(resultState: ResultState<FindClintResponse>) {
        _findOtherClintLiveData.postValue(resultState)
    }

    private suspend fun showFindHasData(result: Deferred<FindClintResponse>) {
        setResultFind(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }
}

