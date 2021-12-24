package com.fooda.balanceapplication.repository

import androidx.room.withTransaction
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.buy.BuyBalanceResponse
import com.fooda.balanceapplication.models.buy.BuyScratchResponse
import com.fooda.balanceapplication.models.charge.ChargeResponse
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.room.ScratchDatabase
import com.fooda.balanceapplication.utilits.networkBoundResource
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    private val api: ScratchAPI,
    private val db: ScratchDatabase,
    private val preferenceHelper: PreferenceHelper
) {
    private val scratchDao = db.scratchDao()

    fun getBalances() = networkBoundResource(
        query = {
            scratchDao.getAllBalances()
        },
        fetch = {
          ///  delay(2000)
            preferenceHelper.getUserID()?.let { api.getBalances(it) }
        },
        saveFetchResult = { list ->
            db.withTransaction {
                scratchDao.deleteAllBalances()
                scratchDao.insertListBalance(list)
            }
        }
    )

suspend fun chargeAccount(): ChargeResponse {
return api.saveChargeOp(preferenceHelper.getUserID()!!)
}
    suspend fun buyBalance(amount: Int,type:String,phone:String): BuyBalanceResponse {
        return api.buyBalance(preferenceHelper.getUserID()!!,amount,type,phone)
    }
    suspend fun buyScratch(amount: Int,type:String): BuyScratchResponse {
        return api.buyScratch(preferenceHelper.getUserID()!!,amount,type)
    }
}