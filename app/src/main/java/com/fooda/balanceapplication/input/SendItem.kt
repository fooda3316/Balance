package com.fooda.balanceapplication.input

import java.io.Serializable

data class SendItem (val type:String,val number:String,val amount:String,val secret:String?=null): Serializable