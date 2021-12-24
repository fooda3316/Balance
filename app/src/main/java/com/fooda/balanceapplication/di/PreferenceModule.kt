package com.fooda.balanceapplication.di

import android.content.Context
import com.fooda.balanceapplication.helpers.PreferenceHelper
import dagger.Module

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun providePreferenceHelper(
        @ApplicationContext context: Context
    ): PreferenceHelper {
        return PreferenceHelper(context)
    }
}