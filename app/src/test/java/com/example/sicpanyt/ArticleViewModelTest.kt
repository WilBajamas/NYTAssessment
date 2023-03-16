package com.example.sicpanyt

import com.example.sicpanyt.dao.ArticleDao
import com.example.sicpanyt.db.ArticleDatabase
import com.example.sicpanyt.entity.ArticleEntity
import com.example.sicpanyt.model.*
import com.example.sicpanyt.repository.ArticleRepository
import com.example.sicpanyt.state.State
import com.example.sicpanyt.ui.articlelist.ArticleListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ArticleViewModelTest {
    private lateinit var articleRepository: ArticleRepository

    private lateinit var articleViewModel: ArticleListViewModel

    private lateinit var articleDatabase: ArticleDatabase

    private val samplePopularArticlesResponse = PopularArticlesResponse(
        listOf(
            PopularArticle(
                "",
                Date()
            ),
            PopularArticle(
                "",
                Date()
            )
        )
    )
    private val samplePopularArticlesEntities =
        listOf(
            ArticleEntity(
                0,
                "type",
                "title",
                "date"
            ),
            ArticleEntity(
                0,
                "type",
                "title",
                "date"
            ),
        )

    private val sampleSearchResponse = SearchArticlesResponse(
        SearchArticlesResponseObject(
            listOf(
                SearchArticle(
                    "",
                    Date()
                ),
                SearchArticle(
                    "",
                    Date()
                )
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        articleRepository = Mockito.mock(ArticleRepository::class.java)
        articleDatabase = Mockito.mock(ArticleDatabase::class.java)
        articleViewModel = ArticleListViewModel(articleRepository, articleDatabase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `set MostEmailed popular article initial data and calls fetchMostPopularArticles correctly`(): Unit =
        runTest {
            val popularArticleType = PopularArticleType.MostEmailed
            `when`(articleRepository.fetchMostPopularArticlesByType(popularArticleType)).thenReturn(
                flow {
                    emit(Result.failure(java.lang.Exception()))
                }
            )
            articleViewModel.setInitialData(ArticleArgument.PopularArticles(popularArticleType))
            Mockito.verify(articleRepository, times(1))
                .fetchMostPopularArticlesByType(popularArticleType)
            Mockito.verify(articleRepository, times(0)).fetchSearchArticles(anyOrNull())
        }

    @Test
    fun `set searchArticles initial data and calls fetchArticleSearchResults correctly`(): Unit =
        runTest {
            val query = "chat"
            `when`(articleRepository.fetchSearchArticles(query)).thenReturn(
                flow {
                    emit(Result.success(sampleSearchResponse))
                }
            )
            articleViewModel.setInitialData(ArticleArgument.SearchArticles(query))
            Mockito.verify(articleRepository, times(1)).fetchSearchArticles(query)
            Mockito.verify(articleRepository, times(0))
                .fetchMostPopularArticlesByType(PopularArticleType.MostEmailed)
            Mockito.verify(articleRepository, times(0))
                .fetchMostPopularArticlesByType(PopularArticleType.MostViewed)
            Mockito.verify(articleRepository, times(0))
                .fetchMostPopularArticlesByType(PopularArticleType.MostShared)
        }

    @Test
    fun `initial state of viewModel is loading`(): Unit = runTest {
        assert(articleViewModel.state.value == State.Loading)
    }

    @Test
    fun `error response from api and state is still Loading`(): Unit = runTest {
        assert(articleViewModel.state.value == State.Loading)

        val popularArticleType = PopularArticleType.MostEmailed
        `when`(articleRepository.fetchMostPopularArticlesByType(popularArticleType)).thenReturn(
            flow {
                emit(Result.success(samplePopularArticlesResponse))
            }
        )

        articleViewModel.setInitialData(ArticleArgument.PopularArticles(popularArticleType))
        assert(articleViewModel.state.value == State.Loading)
    }

    @Test
    fun `successful response from api and state is still Loading`(): Unit = runTest {
        assert(articleViewModel.state.value == State.Loading)

        val popularArticleType = PopularArticleType.MostEmailed
        `when`(articleRepository.fetchMostPopularArticlesByType(popularArticleType)).thenReturn(
            flow {
                emit(Result.success(samplePopularArticlesResponse))
            }
        )
        articleViewModel.setInitialData(ArticleArgument.PopularArticles(popularArticleType))
        assert(articleViewModel.state.value is State.Loading)
    }
}
