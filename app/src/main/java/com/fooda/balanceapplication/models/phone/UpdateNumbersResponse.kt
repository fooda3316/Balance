package com.fooda.balanceapplication.models.phone

import com.fooda.balanceapplication.models.registeration.Phone

data class UpdateNumbersResponse(
    val error: Boolean,
    val message: String,
    val phone: Phone
)