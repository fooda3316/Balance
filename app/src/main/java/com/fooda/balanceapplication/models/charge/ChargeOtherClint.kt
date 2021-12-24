package com.fooda.balanceapplication.models.charge


data class ChargeOtherClint(
    val error: Boolean,
    val message: String,
    val amount: String,
    val balance: Int

)