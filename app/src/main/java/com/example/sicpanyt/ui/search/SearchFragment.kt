package com.example.sicpanyt.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sicpanyt.databinding.FragmentSearchBinding
import com.example.sicpanyt.model.ArticleArgument

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchButton.setOnClickListener {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToArticlesFragment(
                        ArticleArgument.SearchArticles(
                            searchInputField.text.toString()
                        )
                    )
                )
            }

            searchInputField.addTextChangedListener {
                searchButton.isEnabled = !it.isNullOrBlank()
            }
        }
    }

}