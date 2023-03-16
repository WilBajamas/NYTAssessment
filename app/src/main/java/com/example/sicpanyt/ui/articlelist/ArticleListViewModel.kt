package com.example.sicpanyt.ui.articlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sicpanyt.db.ArticleDatabase
import com.example.sicpanyt.entity.ArticleEntity
import com.example.sicpanyt.model.ArticleArgument
import com.example.sicpanyt.model.PopularArticleType
import com.example.sicpanyt.repository.ArticleRepository
import com.example.sicpanyt.state.State
import com.example.sicpanyt.utils.Constants.ARTICLE_SEARCH_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val articleDatabase: ArticleDatabase
) : ViewModel() {

    private val _state: MutableStateFlow<State<List<ArticleEntity>>> =
        MutableStateFlow(State.Loading)
    val state = _state.asStateFlow()

    private lateinit var articleArgument: ArticleArgument

    fun setInitialData(articleArgument: ArticleArgument) {
        this.articleArgument = articleArgument
        fetchData()
    }

    private fun fetchData() {
        // Decide what to do based on argument passed.
        when (articleArgument) {
            is ArticleArgument.PopularArticles -> fetchPopularArticles((articleArgument as ArticleArgument.PopularArticles).type)
            is ArticleArgument.SearchArticles -> searchArticles((articleArgument as ArticleArgument.SearchArticles).query)
        }
    }

    private fun searchArticles(query: String) {
        viewModelScope.launch {
            val result = articleRepository.fetchSearchArticles(query).first()

            if (result.isSuccess && result.getOrNull() != null) {
                val articles = result.getOrNull()!!.response.docs

                withContext(Dispatchers.Default) {
                    articleDatabase.articleDao().insertArticles(articles, ARTICLE_SEARCH_TYPE)
                }.run {
                    _state.emit(State.Loaded(articleDatabase.articleDao().getAll(ARTICLE_SEARCH_TYPE)))
                }

            } else {
                val allArticles = articleDatabase.articleDao().getAll(ARTICLE_SEARCH_TYPE)
                if (allArticles.isNotEmpty()) {
                    _state.emit(State.LoadedFromDataBase(allArticles))
                } else {
                    _state.emit(State.LoadingFailed)
                }
            }
        }
    }

    private fun fetchPopularArticles(popularArticleType: PopularArticleType) {
        viewModelScope.launch {
            val result =
                articleRepository.fetchMostPopularArticlesByType(popularArticleType).first()

            if (result.isSuccess && result.getOrNull() != null) {
                withContext(Dispatchers.Default) {
                    articleDatabase.articleDao().insertArticles(result.getOrNull()!!.articles, popularArticleType.toString())
                }.run {
                    _state.emit(State.Loaded(articleDatabase.articleDao().getAll(popularArticleType.toString())))
                }
            } else {
                val allArticles = articleDatabase.articleDao().getAll(popularArticleType.toString())
                if (allArticles.isNotEmpty()) {
                    _state.emit(State.LoadedFromDataBase(allArticles))
                } else {
                    _state.emit(State.LoadingFailed)
                }

            }
        }
    }

    fun paginate() {
        when (articleArgument) {
            is ArticleArgument.PopularArticles -> paginatePopularArticles((articleArgument as ArticleArgument.PopularArticles).type)
            is ArticleArgument.SearchArticles -> paginateSearchArticles((articleArgument as ArticleArgument.SearchArticles).query)
        }
    }

    private fun paginateSearchArticles(query: String) {
        viewModelScope.launch {
            val result = articleRepository.fetchSearchArticles(query).first()

            if (result.isSuccess && result.getOrNull() != null) {
                val articles = result.getOrNull()!!.response.docs
                articleDatabase.articleDao().insertArticles(articles, ARTICLE_SEARCH_TYPE)
                _state.emit(State.Loaded(articleDatabase.articleDao().getAll(ARTICLE_SEARCH_TYPE)))
            } else {
                _state.emit(State.NextPageLoadingFailed)
            }
        }
    }

    private fun paginatePopularArticles(popularArticleType: PopularArticleType) {
        viewModelScope.launch {
            val result =
                articleRepository.fetchMostPopularArticlesByType(popularArticleType, 30).first()

            if (result.isSuccess && result.getOrNull() != null) {
                articleDatabase.articleDao()
                    .insertArticles(result.getOrNull()!!.articles, popularArticleType.toString())
                _state.emit(
                    State.NextPageLoaded(
                        articleDatabase.articleDao().getAll(popularArticleType.toString())
                    )
                )
            } else {
                _state.emit(State.NextPageLoadingFailed)
            }
        }
    }

    fun retry() {
        fetchData()
    }
}