package com.fooda.balanceapplication.models.balance

data class SendBalanceResponse(
    val balance: List<Balance>,
    val error: Boolean
)