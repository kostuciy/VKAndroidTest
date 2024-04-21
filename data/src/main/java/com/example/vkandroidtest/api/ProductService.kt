package com.example.vkandroidtest.api

import com.example.vkandroidtest.api.dto.ProductResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    fun get(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Observable<ProductResponse>


    @GET("products/search")
    fun search(
        @Query("q") request: String
    ): Observable<ProductResponse>
}