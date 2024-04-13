package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetListUseCase(private val repository: Repository) {

    fun execute(): Flow<List<Product>> =
        repository.list
}