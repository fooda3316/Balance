package com.fooda.balanceapplication.models.services

data class ServicesResponse(
    val error: Boolean,
    val services: List<Service>
)