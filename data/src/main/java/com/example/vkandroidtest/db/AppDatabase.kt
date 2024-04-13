package com.example.vkandroidtest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vkandroidtest.db.dao.ProductDao
import com.example.vkandroidtest.db.entity.Converter
import com.example.vkandroidtest.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): ProductDao
}