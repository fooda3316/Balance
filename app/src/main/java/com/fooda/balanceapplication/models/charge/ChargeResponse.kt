package com.fooda.balanceapplication.models.charge

data class ChargeResponse(
    val amount: Int,
    val balance: Int,
    val error: Boolean,
    val message: String
)