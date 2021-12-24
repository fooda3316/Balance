package com.fooda.balanceapplication.di

import android.app.Activity
import android.content.Context
import com.fooda.balanceapplication.utilits.CustomProgressBar
import com.fooda.balanceapplication.utilits.Dispatcher
import com.fooda.balanceapplication.utilits.DispatcherIm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
object DispatcherModule {
    @Singleton
    @Provides
    fun provideDispatcher(
    ): Dispatcher =
        DispatcherIm()
}

