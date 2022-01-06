package com.acr.topredditsreader.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acr.topredditsreader.databinding.RedditItemBinding
import com.acr.topredditsreader.domain.model.RedditChild
import com.bumptech.glide.Glide

class RedditAdapter : RecyclerView.Adapter<RedditAdapter.RedditHolder>() {

    private var redditDataList: MutableList<RedditChild> = arrayListOf()

    class RedditHolder(itemView: View, redditItemBinding: RedditItemBinding) : RecyclerView.ViewHolder(itemView) {
        var binding: RedditItemBinding = redditItemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditHolder {
        val binding = RedditItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RedditHolder(binding.getRoot(), binding)
    }

    override fun onBindViewHolder(holder: RedditHolder, position: Int) {
        redditDataList.getOrNull(position)?.data?.let { itemData ->
            holder.binding.redditData = itemData

            Glide.with(holder.itemView.context)
                .load(itemData.thumbnail)
                .centerCrop()
                .into(holder.binding.imageThumbnail)
        }
    }

    override fun getItemCount() = redditDataList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun updateRedditList(newData: List<RedditChild>){
        redditDataList = newData as MutableList<RedditChild>
        notifyDataSetChanged()
    }

    fun updateMoreDataRedditList(newData: List<RedditChild>){
        redditDataList.addAll(newData)
        notifyDataSetChanged()
    }
}