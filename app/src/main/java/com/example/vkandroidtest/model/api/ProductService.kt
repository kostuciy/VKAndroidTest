package com.example.vkandroidtest.api

import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.model.dto.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getAll(): Response<List<Product>>

    @GET("products")
    suspend fun getWithSkip(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<ProductList>

    @GET("products?skip={skip}&limit=1") // TODO: check if needed
    suspend fun getById(@Query("skip") postsBefore: Long): Response<Product>

//    TODO: get list sorted by category
}