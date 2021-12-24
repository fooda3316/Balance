package com.fooda.balanceapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.repository.BalanceRepository

import com.fooda.balanceapplication.utilits.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyViewModel @Inject constructor(
    private val balanceRepository: BalanceRepository,
) : ViewModel() {

     lateinit var balanceLiveData : LiveData<Resource<List<Balance>>>
     init {
         viewModelScope.launch {
             balanceLiveData  = balanceRepository.getBalances().asLiveData()

         }
     }




}