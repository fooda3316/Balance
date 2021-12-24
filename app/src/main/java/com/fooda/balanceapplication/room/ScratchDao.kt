package com.fooda.balanceapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.models.sell_history.SellHistory
import com.fooda.balanceapplication.models.services.Service
import kotlinx.coroutines.flow.Flow


@Dao
interface ScratchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuy(balance: Balance): Long

    @Query("SELECT * FROM balances")
    fun getAllBalances(): Flow<List<Balance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListBalance(restaurants: List<Balance>?)

    @Query("DELETE FROM balances")
    suspend fun deleteAllBalances()

    @Query("SELECT * FROM services")
    suspend fun getServices(): List<Service>
  /*********************************************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: Service): Long

    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<Service>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListService(services: List<Service>)

    @Query("DELETE FROM services")
    suspend fun deleteAllServices()
    /********************************************************
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(service: Profile): Long

    @Query("SELECT * FROM profile where id= userId")
    fun getPhone(userId:Int): Flow<List<Service>>


    @Query("DELETE FROM profile where id= ${}")
    suspend fun deletePhone(userId:Int)*/

    /*********************************************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellHistory(service: SellHistory): Long

    @Query("SELECT * FROM sellHistories")
    fun getSellHistories(): Flow<List<SellHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListSellHistory(list: List<SellHistory>)

    @Query("DELETE FROM sellHistories")
    suspend fun deleteAllSellHistories()
}