package com.example.vkandroidtest.repository

import com.example.vkandroidtest.model.ListData
import com.example.vkandroidtest.model.Product
import kotlinx.coroutines.flow.Flow

interface Repository {

    val list: Flow<List<Product>>
    var data: ListData

    suspend fun get(page: Int, limit: Int): List<Product>

    suspend fun getAll(): List<Product>

    suspend fun search(request: String): List<Product>
}