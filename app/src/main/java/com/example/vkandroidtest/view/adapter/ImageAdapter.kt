package com.example.vkandroidtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vkandroidtest.databinding.CardImageBinding
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.utlis.ViewExtensions.loadFromUrl

class ImageAdapter(
) : ListAdapter<String, ImageAdapter.ImageViewHolder>(
    ImageCallback()
) {

    class ImageViewHolder(
        private val binding: CardImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
//            with(binding) {
////                TODO: implement glide download
////                thumbnailImageView (glide bebe)
//                thumbnailImageView.loadFromUrl(product.thumbnail)
//                titleTextView.text = product.title
//                descriptionTextView.text = product.description
//                ratingTextView.text = product.rating.toString()
//                priceTextView.text =
//                    if (product.discountPercentage != 0.0)
//                        (product.price * (1 - product.discountPercentage / 100))
//                            .toInt()
//                            .toString()
//                    else product.price.toString()
//                categoryTextView.text = product.category
////                TODO: set listener to go to detailed fragment
//                root.setOnClickListener {
//                    onInteractionListener.onCardClicked(product)
//                }
//            }
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