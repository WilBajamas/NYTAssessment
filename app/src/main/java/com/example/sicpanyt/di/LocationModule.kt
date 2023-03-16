package com.example.sicpanyt.di

import com.example.sicpanyt.services.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class LocationModule {

    @Provides
    fun provideLocationServices(): LocationService {
        return LocationService()
    }
}