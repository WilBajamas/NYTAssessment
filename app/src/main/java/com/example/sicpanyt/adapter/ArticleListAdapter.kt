package com.example.sicpanyt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sicpanyt.databinding.ArticleListItemBinding
import com.example.sicpanyt.entity.ArticleEntity

class ArticleListAdapter(private val items: List<ArticleEntity>): RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    fun addArticles(newList: List<ArticleEntity>) {
        val startPosition = items.size
        items.toMutableList().addAll(newList)
        notifyItemRangeInserted(startPosition, newList.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleListAdapter.ViewHolder {
        val binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.title
        holder.binding.date.text = item.datePublished
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root)
}