package com.fooda.balanceapplication.repository

import androidx.room.withTransaction
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.room.ScratchDatabase
import com.fooda.balanceapplication.utilits.networkBoundResource
import javax.inject.Inject

class SellHistoryRepository @Inject constructor(
    private val api: ScratchAPI,
    private val db: ScratchDatabase,
    private val preferenceHelper: PreferenceHelper
) {

    private val scratchDao = db.scratchDao()

    fun displaySellHistory() = networkBoundResource(
        query = {
            scratchDao.getSellHistories()
        },
        fetch = {
            api.displaySellHistory(preferenceHelper.getUserID()!!)
        },
        saveFetchResult = { response ->
            db.withTransaction {
                scratchDao.deleteAllSellHistories()
                scratchDao.insertListSellHistory(response)
            }
        }
    )



}