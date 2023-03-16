package com.example.sicpanyt.api

import com.example.sicpanyt.model.SearchArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchArticlesApi {
    @GET("search/v2/articlesearch.json")
    suspend fun searchArticles(@Query("q") query: String): Response<SearchArticlesResponse>
}