package com.example.vkandroidtest.repository

import com.example.vkandroidtest.api.ProductService
import com.example.vkandroidtest.api.dto.ProductResponse
import com.example.vkandroidtest.db.dao.ProductDao
import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.model.ListData
import com.example.vkandroidtest.utils.ModelUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) : Repository {

    override val list: Flow<List<Product>> =
        dao.get().map { postEntityList ->
            postEntityList.map { ModelUtils.toModel(it) }
        }.flowOn(Dispatchers.Main)

    override var data = ListData()

    override suspend fun get(page: Int, limit: Int): List<Product> {
        try {
            val skipAmount = (page - 1) * 20

            val response = service.get(skipAmount, limit)
            if (!response.isSuccessful)
                throw IOException("get() failed, code: ${response.code()}")

            val body =
                response.body() ?: throw IOException("get() body is null, code: ${response.code()}")

            updateLocalData(body)
            return body.products
        } catch (e: IOException) {
            throw IOException("Network error")
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    //    gets all data from server, use getPage() to receive only part of it
    override suspend fun getAll(): List<Product> = get(1, 0)

    override suspend fun search(request: String): List<Product> {
        try {
            val response = service.search(request)
            if (!response.isSuccessful)
                throw IOException("get() failed, code: ${response.code()}")

            val body =
                response.body() ?: throw IOException("get() body is null, code: ${response.code()}")

            updateLocalData(body, true)
            return body.products
        } catch (e: IOException) {
            throw IOException("Network error")
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    private suspend fun updateLocalData(
        productResponse: ProductResponse,
        isSearching: Boolean = false
    ) {
        dao.clear()
        with(productResponse) {
            dao.insert(
                products.map { ModelUtils.toEntity(it) }
            )

            if (isSearching) return
            data = ListData(
                total, skip, limit
            )
        }
    }
}