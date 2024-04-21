package com.example.vkandroidtest.repository

import com.example.vkandroidtest.model.ListData
import com.example.vkandroidtest.model.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface Repository {

    val list: Flowable<List<Product>>
    var data: ListData

    fun get(page: Int, limit: Int): Completable

    fun getAll(): Completable

    fun search(request: String): Completable

    fun clearDisposables()
}