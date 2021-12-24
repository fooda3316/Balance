package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.models.sell_history.SellHistory
import com.fooda.balanceapplication.repository.SellHistoryRepository
import com.fooda.balanceapplication.utilits.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellHistoryViewModel @Inject constructor(
        private val repository: SellHistoryRepository,
) : ViewModel() {

     lateinit var liveData: LiveData<Resource<List<SellHistory>>>
     init {
         viewModelScope.launch {
             liveData = repository.displaySellHistory().asLiveData()
         }
     }



}