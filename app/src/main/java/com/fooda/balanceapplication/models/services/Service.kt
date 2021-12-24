package com.fooda.balanceapplication.models.services

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
        @PrimaryKey val id: Int,
        val name: String,
        val company: String,
        val code: String,
        val price: String,
        val date: String



)
//val code: String,
//val company: String,
//val date: String,
//@PrimaryKey val id: Int,
//val name: String,
//val price: String