package com.example.vkandroidtest.api

import com.example.vkandroidtest.api.dto.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun get(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<ProductResponse>


    @GET("products/search")
    suspend fun search(
        @Query("q") request: String
    ): Response<ProductResponse>
}