package com.example.vkandroidtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vkandroidtest.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

//    as db only has 1 page of product data,
//    it actually gets only 20 objects, not all
    @Query("SELECT * FROM products")
    fun get(): Flow<List<ProductEntity>> // TODO: check if works with state flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE id IN (:idList)")
    suspend fun delete(idList: List<Int>)   // TODO: check if needed

    @Query("DELETE FROM products")
    suspend fun clear()

    // TODO: get by category
}