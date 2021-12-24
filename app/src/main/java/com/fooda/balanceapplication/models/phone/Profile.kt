package com.fooda.balanceapplication.models.phone

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = false)
   val id: Int,
    val name: String?,
    val balance: Int?,
    val zain: String?,
    val mtn: String?,
    val sudani: String?


)