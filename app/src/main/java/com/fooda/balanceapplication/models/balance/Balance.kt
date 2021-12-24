package com.fooda.balanceapplication.models.balance

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "balances")
data class Balance(
        val name: String,
        val type: String,
        val amount: String,
        val scratch: String,
        @PrimaryKey(autoGenerate = false)
        val date: String

)