package com.example.sicpanyt.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sicpanyt.R
import com.example.sicpanyt.adapter.MainSectionAdapter
import com.example.sicpanyt.databinding.FragmentMainBinding
import com.example.sicpanyt.model.*

class MainFragment : Fragment() {

    private lateinit var adapter: MainSectionAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionItems = listOf(
            Section.SectionTitle(getString(R.string.search)),
            Section.SectionItem(
                SectionItemIdentifier.SearchArticles,
                getString(R.string.search_articles)
            ),
            Section.SectionTitle(getString(R.string.popular)),
            Section.SectionItem(SectionItemIdentifier.MostViewed, getString(R.string.most_viewed)),
            Section.SectionItem(SectionItemIdentifier.MostShared, getString(R.string.most_shared)),
            Section.SectionItem(SectionItemIdentifier.MostEmailed, getString(R.string.most_emailed))
        )

        adapter = MainSectionAdapter(sectionItems) { itemIdentifier ->
            onClickSectionItem(itemIdentifier)
        }

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun onClickSectionItem(identifier: SectionItemIdentifier) {
        when (identifier) {
            SectionItemIdentifier.SearchArticles -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
            }
            SectionItemIdentifier.MostViewed -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticleArgument.PopularArticles(PopularArticleType.MostViewed)
                    )
                )
            }
            SectionItemIdentifier.MostShared -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticleArgument.PopularArticles(PopularArticleType.MostShared)
                    )
                )
            }
            SectionItemIdentifier.MostEmailed -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticleArgument.PopularArticles(PopularArticleType.MostEmailed)
                    )
                )
            }
        }
    }
}