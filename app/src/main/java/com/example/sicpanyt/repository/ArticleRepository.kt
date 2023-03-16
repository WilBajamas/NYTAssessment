package com.example.sicpanyt.repository

import com.example.sicpanyt.model.PopularArticleType
import com.example.sicpanyt.model.PopularArticlesResponse
import com.example.sicpanyt.model.SearchArticlesResponse
import kotlinx.coroutines.flow.Flow

 interface ArticleRepository  {

     suspend fun fetchMostPopularArticlesByType(
        type: PopularArticleType,
        period: Int = 7
     ): Flow<Result<PopularArticlesResponse>>

    suspend fun fetchSearchArticles(
        query: String,
    ): Flow<Result<SearchArticlesResponse>>
}
