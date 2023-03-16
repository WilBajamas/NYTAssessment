package com.example.sicpanyt.di

import android.content.Context
import androidx.room.Room
import com.example.sicpanyt.db.ArticleDatabase
import com.example.sicpanyt.utils.Constants.ARTICLE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java, ARTICLE_DATABASE_NAME
        ).build()
    }
}