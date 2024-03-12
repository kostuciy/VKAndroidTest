package com.example.vkandroidtest.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vkandroidtest.model.db.dao.ProductDao
import com.example.vkandroidtest.model.db.entity.Converter
import com.example.vkandroidtest.model.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): ProductDao
}