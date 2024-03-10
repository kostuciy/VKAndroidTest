package com.example.vkandroidtest.model.dto

import com.example.vkandroidtest.entity.ProductEntity

data class Product(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val category: String? = null, // TODO: check if all products have category
    val thumbnail: String,
    val images: List<String> = emptyList() // TODO: should I save it with GSON?
) {
    fun entity() =
        ProductEntity(
            id, title, description, price, discountPercentage,
            rating, category, thumbnail, images
        )
}