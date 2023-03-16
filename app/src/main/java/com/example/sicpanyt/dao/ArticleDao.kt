package com.example.sicpanyt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.sicpanyt.entity.ArticleEntity
import com.example.sicpanyt.model.Article
import com.example.sicpanyt.utils.DateUtils

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertAll(articleEntities: List<ArticleEntity>)

    @Transaction
    suspend fun insertArticles(articles: List<Article>, type: String) {
        val newArticleEntities = articles.map {
            ArticleEntity(
                type = type,
                title = it.title,
                datePublished = DateUtils.toDayMonthYearFormat(it.datePublished)
            )
        }
        insertAll(newArticleEntities)
    }

    @Query("SELECT * FROM articles_entity WHERE type = :articleType")
    suspend fun getAll(articleType: String): List<ArticleEntity>
}