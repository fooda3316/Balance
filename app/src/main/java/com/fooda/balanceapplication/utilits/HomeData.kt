package com.fooda.balanceapplication.utilits

import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.models.Home
import java.io.Serializable
import java.util.ArrayList

object HomeData {
    private var homeList: MutableList<Home>? = null
    private var mainList: MutableList<String>? = null
    fun getHomeList(): MutableList<Home>? {
        homeList = ArrayList<Home>()
        (homeList as ArrayList<Home>).add(Home(R.string.services, R.drawable.ic_home))
        (homeList as ArrayList<Home>).add(Home(R.string.buy_balance, R.drawable.ic_buy_balance))
        (homeList as ArrayList<Home>).add(Home(R.string.send_balance, R.drawable.ic_send_balance))
        (homeList as ArrayList<Home>).add(Home(R.string.charge_other_clint, R.drawable.ic_home))
        (homeList as ArrayList<Home>).add(Home(R.string.charge_wallet, R.drawable.ic_home))
        (homeList as ArrayList<Home>).add(Home(R.string.buy_scratch, R.drawable.ic_home))
        (homeList as ArrayList<Home>).add(Home(R.string.sell_history, R.drawable.ic_home))

        return homeList
    }

    fun getMainList(): MutableList<String>? {
        mainList = ArrayList<String>()
        (mainList as ArrayList<String>).add("50")
        (mainList as ArrayList<String>).add("100")
        (mainList as ArrayList<String>).add("250")
        (mainList as ArrayList<String>).add("500")
        (mainList as ArrayList<String>).add("750")
        (mainList as ArrayList<String>).add("1000")
        (mainList as ArrayList<String>).add("2500")
        (mainList as ArrayList<String>).add("5000")

        return mainList
    }



}

