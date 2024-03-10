package com.example.vkandroidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vkandroidtest.databinding.CardProductBinding
import com.example.vkandroidtest.model.dto.Product

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
                titleTextView.text = product.title
                descriptionTextView.text = product.description
//                TODO: set listener to go to detailed fragment
//                root.setOnClickListener {} (onInteractionListener)
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