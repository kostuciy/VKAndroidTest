package com.example.vkandroidtest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkandroidtest.R
import com.example.vkandroidtest.databinding.FragmentProductBinding
import com.example.vkandroidtest.utlis.ViewExtensions.loadFromUrl
import com.example.vkandroidtest.view.adapter.ImageAdapter
import com.example.vkandroidtest.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductBinding.inflate(inflater, container, false)
        val product = viewModel.currentProduct

        with(binding) {
            with(imageRecyclerView) {
                this.adapter = ImageAdapter().apply {
                    submitList(product.images)
                }
                layoutManager = GridLayoutManager(
                    context,
                    1
                )
            }
            thumbnailImageView.loadFromUrl(product.thumbnail)
            titleTextView.text = product.title
            ratingTextView.text = product.rating.toString()
            priceTextView.text = if (product.discountPercentage != 0.toDouble())
                resources.getString(
                    R.string.price_discount_text,
                    (product.price * (1 - product.discountPercentage / 100)).toInt(),
                    product.discountPercentage.toInt(),
                    product.price
                )
                else product.price.toString()
            stockTextView.text = resources.getString(R.string.text_stock, product.stock)
            descriptionTextView.text = product.description
            categoryTextView.text = product.category
        }

        return binding.root
    }
}