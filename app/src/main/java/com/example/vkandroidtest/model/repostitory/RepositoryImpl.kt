package com.example.vkandroidtest.model.repostitory

import com.example.vkandroidtest.api.ProductService
import com.example.vkandroidtest.dao.ProductDao
import com.example.vkandroidtest.model.dto.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import kotlin.math.max

class RepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) : Repository {

    override val data: Flow<List<Product>> =
        dao.get().map { postEntityList ->
            postEntityList.map { it.dto() }
        }.flowOn(Dispatchers.Main)


    //    gets all data from server, use getPage() to receive only part of it
    override suspend fun getAll(): List<Product> {
        try {
            val response = service.getAll()
            if (!response.isSuccessful)
                throw IOException("getAll() failed, code: ${response.code()}")

            val body = response.body() ?:
                throw IOException("getAll() body is null, code: ${response.code()}")
            insertToDb(body) // TODO: fix

            return body
        } catch (e: IOException) {
            throw IOException("Network error")
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    override suspend fun get(page: Int): List<Product> {
        try {
            val skipAmount = max((page - 1) * 20, 0)
            val response = service.getWithSkip(skipAmount, 20)
            if (!response.isSuccessful)
                throw IOException("get() failed, code: ${response.code()}")

            val body =
                response.body() ?: throw IOException("get() body is null, code: ${response.code()}")
            val products = body.products
            insertToDb(products)

            return products
        } catch (e: IOException) {
            throw IOException("Network error")
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    override suspend fun getById(id: Long): Product {
        try {
            val postsBefore = id - 1
            val response = service.getById(postsBefore)
            if (!response.isSuccessful)
                throw IOException("get() failed, code: ${response.code()}")

            val body = response.body() ?:
                throw IOException("get() body is null, code: ${response.code()}")


            return body
        } catch (e: IOException) {
            throw IOException("Network error")
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    private suspend fun insertToDb(product: List<Product>) {
        dao.clear()     // TODO: redo, not the best way to have only 1 page in db
        product.map { it.entity() }.let {
            dao.insert(it)
        }
    }
}