package com.example.vkandroidtest.model.dto

data class ProductResponse(
    val products: List<Product> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 20
)