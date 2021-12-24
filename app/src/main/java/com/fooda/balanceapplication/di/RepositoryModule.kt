package com.fooda.balanceapplication.di

import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.repository.BalanceRepository
import com.fooda.balanceapplication.repository.ServiceRepository
import com.fooda.balanceapplication.retrofit.ScratchAPI
import com.fooda.balanceapplication.room.ScratchDao
import com.fooda.balanceapplication.room.ScratchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBuyRepository(
        scratchDatabase: ScratchDatabase,
          retrofit: ScratchAPI,
        preferenceHelper: PreferenceHelper
    ): BalanceRepository {
        return BalanceRepository(retrofit,scratchDatabase,preferenceHelper )
    }
    @Singleton
    @Provides
    fun provideServiceRepository(
            scratchDatabase: ScratchDatabase,
            retrofit: ScratchAPI
    ): ServiceRepository {
        return ServiceRepository(retrofit,scratchDatabase )
    }
}














