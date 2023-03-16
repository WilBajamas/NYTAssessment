package com.example.sicpanyt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sicpanyt.dao.ArticleDao
import com.example.sicpanyt.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}