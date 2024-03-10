package com.example.vkandroidtest.model.repostitory

import com.example.vkandroidtest.model.dto.Product
import kotlinx.coroutines.flow.Flow

interface Repository {

    val data: Flow<List<Product>>

    suspend fun getAll(): List<Product>

//    loads 20 products:
//    page == 1 -> https://dummyjson.com/products?skip=0&limit=20
//    page == 2 -> https://dummyjson.com/products?skip=20&limit=20
//    ...
    suspend fun get(page: Int): List<Product>

    suspend fun getById(id: Long): Product
}