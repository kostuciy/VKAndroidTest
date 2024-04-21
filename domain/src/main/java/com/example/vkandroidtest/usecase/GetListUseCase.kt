package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.repository.Repository
import io.reactivex.rxjava3.core.Flowable

class GetListUseCase(private val repository: Repository) {

    fun execute(): Flowable<List<Product>> =
        repository.list
}