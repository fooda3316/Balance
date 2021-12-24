package com.fooda.balanceapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fooda.balanceapplication.models.services.Service
import com.fooda.balanceapplication.repository.ServiceRepository
import com.fooda.balanceapplication.utilits.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
        private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private  val TAG = "BuyViewModel"
     lateinit var services: LiveData<Resource<List<Service>>>
     init {
         viewModelScope.launch {
             services = serviceRepository.getService().asLiveData()
         }
     }



}