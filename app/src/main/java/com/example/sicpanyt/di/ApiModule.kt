package com.example.sicpanyt.di

import com.example.sicpanyt.api.PopularArticlesApi
import com.example.sicpanyt.api.SearchArticlesApi
import com.example.sicpanyt.services.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideSearchArticleApi(): SearchArticlesApi {
        return Network.retrofit.create(SearchArticlesApi::class.java)
    }

    @Provides
    @Singleton
    fun providePopularArticleApi(): PopularArticlesApi {
        return Network.retrofit.create(PopularArticlesApi::class.java)
    }
}