package com.fooda.balanceapplication.repository

import androidx.room.withTransaction
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.buy.BuyBalanceResponse
import com.fooda.balanceapplication.models.buy.BuyScratchResponse
import com.fooda.balanceapplication.models.charge.ChargeHistory
import com.fooda.balanceapplication.models.charge.ChargeResponse
import com.fooda.balanceapplication.models.registeration.FindClintResponse
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.room.ScratchDatabase
import com.fooda.balanceapplication.utilits.networkBoundResource
import javax.inject.Inject

class ChargeOtherClintRepository @Inject constructor(
    private val api: ScratchAPI,
    private val preferenceHelper: PreferenceHelper
) {

    suspend fun chargeOtherClint(clintId: Int,amount: Int): ChargeHistory {
        return api.chargeOtherClint(preferenceHelper.getUserID()!!,clintId,amount)
    }
    suspend fun lookForClint(phone:String): FindClintResponse {
        return api.lookForClint(phone)
    }

}