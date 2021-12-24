package com.fooda.balanceapplication.models.registeration

data class RegisterResponse(
    val error: Boolean,
    val user: User,
    val phone:Phone,
    val token: String,
    val message: String


)