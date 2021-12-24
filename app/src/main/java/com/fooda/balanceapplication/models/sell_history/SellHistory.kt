package com.fooda.balanceapplication.models.sell_history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sellHistories")
data class SellHistory(
    val amount: String,
    val date: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val scratch: String,
    val type: String
)