package com.example.vkandroidtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vkandroidtest.databinding.CardImageBinding
import com.example.vkandroidtest.utlis.ViewExtensions.loadFromUrl

class ImageAdapter : ListAdapter<String, ImageAdapter.ImageViewHolder>(
    ImageCallback()
) {

    class ImageViewHolder(
        private val binding: CardImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.imageView.loadFromUrl(url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = CardImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }
}

class ImageCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}