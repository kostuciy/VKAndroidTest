package com.example.vkandroidtest.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vkandroidtest.db.entity.ProductEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

//    as db only has 1 page of product data,
//    it actually gets only 20 objects, not all
    @Query("SELECT * FROM products")
    fun get(): Flowable<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<ProductEntity>): Completable

    @Query("DELETE FROM products WHERE id IN (:idList)")
    fun delete(idList: List<Int>): Completable

    @Query("DELETE FROM products")
    fun clear(): Completable
}