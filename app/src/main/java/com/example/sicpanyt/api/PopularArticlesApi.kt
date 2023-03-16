package com.example.sicpanyt.api

import com.example.sicpanyt.model.PopularArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PopularArticlesApi {
    @GET("mostpopular/v2/viewed/{period}.json")
    suspend fun getMostViewedArticles(@Path("period") period: Int): Response<PopularArticlesResponse>

    @GET("mostpopular/v2/emailed/{period}.json")
    suspend fun getMostEmailedArticles(@Path("period") period: Int): Response<PopularArticlesResponse>

    @GET("mostpopular/v2/shared/{period}.json")
    suspend fun getMostSharedArticles(@Path("period") period: Int): Response<PopularArticlesResponse>
}
