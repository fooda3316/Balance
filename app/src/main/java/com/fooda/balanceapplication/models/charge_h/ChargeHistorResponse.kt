package com.fooda.balanceapplication.models.charge_h

data class ChargeHistorResponse(
    val chargeHistories: List<ChargeHistory>,
    val error: Boolean
)