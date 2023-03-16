package com.example.sicpanyt.ui.articlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sicpanyt.adapter.ArticleListAdapter
import com.example.sicpanyt.databinding.FragmentArticleListBinding
import com.example.sicpanyt.state.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment() {

    private val viewModel: ArticleListViewModel by viewModels()
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var adapter: ArticleListAdapter
    private val args: ArticleListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setInitialData(args.articleFragmentArgument)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val visibleItemCount = layoutManager!!.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // Load more items.
                    viewModel.paginate()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBarWrapper.isVisible = state is State.Loading
                binding.errorLayoutWrapper.isVisible = state is State.LoadingFailed
                binding.paginationProgressBar.isVisible = state is State.NextPageLoading

                when (state) {
                    is State.Loaded -> {
                        adapter = ArticleListAdapter(state.data)
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.recyclerView.adapter = adapter
                    }
                    is State.LoadedFromDataBase -> {
                        adapter = ArticleListAdapter(state.data)
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.recyclerView.adapter = adapter
                    }
                    is State.NextPageLoaded -> {
                        adapter.addArticles(state.data)
                    }
                    State.Loading -> Unit
                    State.LoadingFailed -> Unit
                    State.NextPageLoading -> Unit
                    State.NextPageLoadingFailed -> Unit
                }
            }
        }

        binding.errorLayout.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }
}