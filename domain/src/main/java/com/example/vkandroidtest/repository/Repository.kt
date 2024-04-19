package com.example.vkandroidtest.repository

import com.example.vkandroidtest.model.ListData
import com.example.vkandroidtest.model.Product
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface Repository {

    val list: Flowable<List<Product>>
    var data: ListData

    fun get(page: Int, limit: Int): List<Product>

    fun getAll(): List<Product>

    fun search(request: String): List<Product>
}