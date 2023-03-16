package com.example.sicpanyt.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PopularArticlesResponse(
    @SerializedName("results")
    val articles: List<PopularArticle>
)

data class PopularArticle(
    @SerializedName("title")
    override val title: String,
    @SerializedName("published_date")
    override val datePublished: Date
): Article
