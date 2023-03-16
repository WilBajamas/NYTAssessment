package com.example.sicpanyt.di

import com.example.sicpanyt.api.PopularArticlesApi
import com.example.sicpanyt.api.SearchArticlesApi
import com.example.sicpanyt.repository.ArticleRepository
import com.example.sicpanyt.repository.DefaultArticleRepository
import com.example.sicpanyt.services.NetworkRequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideArticleRepository(
        popularArticlesApi: PopularArticlesApi,
        searchArticlesApi: SearchArticlesApi,
        networkRequestManager: NetworkRequestManager
    ): ArticleRepository {
        return DefaultArticleRepository(popularArticlesApi, searchArticlesApi, networkRequestManager)
    }
}