package com.example.sicpanyt.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchArticlesResponse(
    val response: SearchArticlesResponseObject
)

data class SearchArticlesResponseObject(
    val docs: List<SearchArticle>
)

data class SearchArticle(
    @SerializedName("abstract")
    override val title: String,
    @SerializedName("pub_date")
    override val datePublished: Date
) : Article
