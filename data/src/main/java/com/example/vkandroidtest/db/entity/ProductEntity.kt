package com.example.vkandroidtest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double = 0.0,
    val stock: Int = 0,
    val rating: Double = 0.0,
    val category: String? = null, // TODO: check if all products have category
    val thumbnail: String,
    val images: List<String> = emptyList() // TODO: should I save it with GSON?
)

class Converter {
    @TypeConverter
    fun jsonFromImages(images: List<String>): String =
        Gson().toJson(images)

    @TypeConverter
    fun imagesFromJson(json: String): List<String> =
        Gson().fromJson(
            json,
            object : TypeToken<List<String>>() {}.type
        )
    }