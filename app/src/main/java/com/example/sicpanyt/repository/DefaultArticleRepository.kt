package com.example.sicpanyt.repository

import com.example.sicpanyt.api.PopularArticlesApi
import com.example.sicpanyt.api.SearchArticlesApi
import com.example.sicpanyt.model.PopularArticleType
import com.example.sicpanyt.model.PopularArticlesResponse
import com.example.sicpanyt.model.SearchArticlesResponse
import com.example.sicpanyt.services.NetworkRequestManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultArticleRepository @Inject constructor(
    private val popularArticlesApi: PopularArticlesApi,
    private val searchArticlesApi: SearchArticlesApi,
    private val networkRequestManager: NetworkRequestManager
) : ArticleRepository{

    override suspend fun fetchMostPopularArticlesByType(
        type: PopularArticleType,
        period: Int
    ): Flow<Result<PopularArticlesResponse>> = flow{
        val result = networkRequestManager.callApi {
            when (type) {
                PopularArticleType.MostViewed -> popularArticlesApi.getMostViewedArticles(period)
                PopularArticleType.MostEmailed -> popularArticlesApi.getMostEmailedArticles(period)
                PopularArticleType.MostShared -> popularArticlesApi.getMostSharedArticles(period)
            }
        }
        emit(result)
    }

    override suspend fun fetchSearchArticles(query: String): Flow<Result<SearchArticlesResponse>> = flow{
        val result = networkRequestManager.callApi {
            searchArticlesApi.searchArticles(query)
        }

        emit(result)
    }
}