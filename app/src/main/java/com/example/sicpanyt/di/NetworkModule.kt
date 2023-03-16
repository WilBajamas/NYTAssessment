package com.example.sicpanyt.di

import com.example.sicpanyt.services.NetworkRequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkRequestManager(): NetworkRequestManager {
        return NetworkRequestManager()
    }
}
