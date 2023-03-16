package com.example.sicpanyt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sicpanyt.databinding.SectionItemBinding
import com.example.sicpanyt.databinding.SectionTitleBinding
import com.example.sicpanyt.model.Section
import com.example.sicpanyt.model.SectionItemIdentifier

class MainSectionAdapter(
    private val items: List<Section>, private val listener: (SectionItemIdentifier) -> Unit
) : RecyclerView.Adapter<MainSectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ITEM_TYPE_SECTION_TITLE) {
            val binding =
                SectionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SectionTitleViewHolder(binding)
        } else {
            val binding =
                SectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SectionItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SectionTitleViewHolder -> {
                val item = items[position] as Section.SectionTitle
                holder.binding.title.text = item.title
            }
            is SectionItemViewHolder -> {
                val item = items[position] as Section.SectionItem
                holder.binding.title.text = item.title
                holder.binding.root.setOnClickListener {
                    listener(item.identifier)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Section.SectionTitle -> ITEM_TYPE_SECTION_TITLE
            is Section.SectionItem -> ITEM_TYPE_SECTION_CATEGORY_ITEM
        }
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class SectionTitleViewHolder(val binding: SectionTitleBinding) : ViewHolder(binding.root)
    inner class SectionItemViewHolder(val binding: SectionItemBinding) : ViewHolder(binding.root)

    companion object {
        const val ITEM_TYPE_SECTION_TITLE = 0
        const val ITEM_TYPE_SECTION_CATEGORY_ITEM = 1
    }
}
