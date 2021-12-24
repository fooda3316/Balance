package com.fooda.balanceapplication.models.buy

data class BuyScratchResponse(
        val error: Boolean,
        val message: String,
        val amount: Int,
        val balance: Int,
        val scratch: String

)