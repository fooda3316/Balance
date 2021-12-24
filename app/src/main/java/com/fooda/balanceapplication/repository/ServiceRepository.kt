package com.fooda.balanceapplication.repository

import androidx.room.withTransaction
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.room.ScratchDatabase
import com.fooda.balanceapplication.utilits.networkBoundResource
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val api: ScratchAPI,
    private val db: ScratchDatabase
) {
    private  val TAG = "ServiceRepository"
    init {

    }
    private val scratchDao = db.scratchDao()

    fun getService() = networkBoundResource(
        query = {
            scratchDao.getAllServices()
        },
        fetch = {
            api.getServices()
        },
        saveFetchResult = { service ->
//            Log.d(TAG, "getService name : ${service} ")
//            service.forEach { service ->
//                //Log.d(TAG, "getService name : ${service.name} ")
//            }
            db.withTransaction {
                scratchDao.deleteAllServices()
                scratchDao.insertListService(service)
            }
        }
    )



}