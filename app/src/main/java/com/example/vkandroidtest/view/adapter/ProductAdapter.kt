package com.example.vkandroidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vkandroidtest.databinding.CardProductBinding
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.utlis.ViewExtensions.loadFromUrl

interface OnInteractionListener {
    fun onCardClicked(product: Product)
}

class ProductAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(
   ProductCallback()
) {

    class ProductViewHolder(
        private val binding: CardProductBinding,
        private val onInteractionListener: OnInteractionListener
        ) : ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
//                TODO: implement glide download
//                thumbnailImageView (glide bebe)
                thumbnailImageView.loadFromUrl(product.thumbnail)
                titleTextView.text = product.title
                descriptionTextView.text = product.description
                ratingTextView.text = product.rating.toString()
                priceTextView.text =
                    if (product.discountPercentage != 0.0)
                        (product.price * (1 - product.discountPercentage / 100))
                            .toInt()
                            .toString()
                    else product.price.toString()
                categoryTextView.text = product.category
//                TODO: set listener to go to detailed fragment
                root.setOnClickListener {
                    onInteractionListener.onCardClicked(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CardProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }
}

class ProductCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}