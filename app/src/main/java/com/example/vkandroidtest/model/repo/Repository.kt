package com.example.vkandroidtest.model.repo

import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.model.state.ListData
import kotlinx.coroutines.flow.Flow

interface Repository {

    val list: Flow<List<Product>>
    var data: ListData

    suspend fun get(page: Int, limit: Int): List<Product>

    suspend fun getAll(): List<Product>
}