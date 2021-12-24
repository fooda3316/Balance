package com.fooda.balanceapplication.di

import android.content.Context
import androidx.room.Room
import com.fooda.balanceapplication.room.ScratchDao
import com.fooda.balanceapplication.room.ScratchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): ScratchDatabase {
        return Room
            .databaseBuilder(
                context,
                ScratchDatabase::class.java,
                ScratchDatabase.DATABASE_NAME)
           // .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(scratchDatabase: ScratchDatabase): ScratchDao {
        return scratchDatabase.scratchDao()
    }
}