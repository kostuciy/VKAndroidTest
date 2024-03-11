package com.example.vkandroidtest.model.repo

import android.util.Log
import com.example.vkandroidtest.api.ProductService
import com.example.vkandroidtest.dao.ProductDao
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.model.dto.ProductResponse
import com.example.vkandroidtest.model.state.ListData
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
            postEntityList.map { it.dto() }
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
            throw IOException("Network error") // TODO: redo exceptions
        } catch (e: Exception) {
            throw Exception("Unexpected error")
        }
    }

    //    gets all data from server, use getPage() to receive only part of it
    override suspend fun getAll(): List<Product> = get(1, 0)
//        try {
//            val response = service.getAll()
//            if (!response.isSuccessful)
//                throw IOException("getAll() failed, code: ${response.code()}")
//
//            val body = response.body() ?:
//                throw IOException("getAll() body is null, code: ${response.code()}")
//            val products = body.products
////            insertToDb(products) // TODO: fix
//
//            return products
//        } catch (e: IOException) {
//            throw IOException("Network error")
//        } catch (e: Exception) {
//            throw Exception("Unexpected error")
//        }


    private suspend fun updateLocalData(productResponse: ProductResponse) {
        dao.clear()     // TODO: redo, not the best way to have only 1 page in db
        with(productResponse) {
            dao.insert(
                products.map { it.entity() }
            )
            data = ListData(
                total, skip, limit
            )
            Log.d("ST-E", "data - ${data}")
        }
    }
}