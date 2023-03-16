package com.example.sicpanyt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ArticleArgument : Parcelable {

    @Parcelize
    class PopularArticles(val type: PopularArticleType) : ArticleArgument(), Parcelable

    @Parcelize
    class SearchArticles(val query: String) : ArticleArgument(), Parcelable
}