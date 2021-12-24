package com.fooda.balanceapplication.models.buy

data class BuyBalanceResponse(
        val error: Boolean,
        val message: String,
        val amount: Int,
        val balance: Int

)