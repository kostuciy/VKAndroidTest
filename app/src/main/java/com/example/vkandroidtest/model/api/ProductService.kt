package com.example.vkandroidtest.api

import com.example.vkandroidtest.model.dto.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getAll(): Response<List<Product>>

    @GET("products?skip={skip}&limit=20")
    suspend fun getWithSkip(@Path("skip") skip: Int): Response<List<Product>>

    @GET("products?skip={skip}&limit=1") // TODO: check if needed
    suspend fun getById(@Path("skip") postsBefore: Long): Response<Product>

//    TODO: get list sorted by category
}