package com.fooda.balanceapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.models.sell_history.SellHistory
import com.fooda.balanceapplication.models.services.Service

@Database(entities = [Balance::class ,Service::class, SellHistory::class], version = 1,exportSchema = false)
abstract class ScratchDatabase: RoomDatabase() {

    abstract fun scratchDao(): ScratchDao
    companion object {
        val DATABASE_NAME: String = "scratch_db"
    }
//    companion object {
//        val DATABASE_NAME: String = "scratch_db"
//
//        @Volatile
//        private var instance: ScratchDatabase? = null
//        private var LOCK = Any()
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context): ScratchDatabase {
//            return Room.databaseBuilder(
//                context,
//                ScratchDatabase::class.java,
//                DATABASE_NAME
//            )
//                // .fallbackToDestructiveMigration()
//                .build()
//
//        }
//    }


}