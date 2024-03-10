package com.example.vkandroidtest.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.vkandroidtest.model.dto.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val category: String? = null, // TODO: check if all products have category
    val thumbnail: String,
    val images: List<String> = emptyList() // TODO: should I save it with GSON?
) {
    fun dto() = Product(
        id, title, description, price, discountPercentage,
        rating, category, thumbnail, images
    )
}

// json^
//"id": 41,
//"title": "NIGHT SUIT",
//"description": "NIGHT SUIT RED MICKY MOUSE..  For Girls. Fantastic Suits.",
//"price": 55,
//"discountPercentage": 15.05,
//"rating": 4.65,
//"stock": 21,
//"brand": "RED MICKY MOUSE..",
//"category": "womens-dresses",
//"thumbnail": "https://cdn.dummyjson.com/product-images/41/thumbnail.webp",
//"images": [
//"https://cdn.dummyjson.com/product-images/41/1.jpg",
//"https://cdn.dummyjson.com/product-images/41/2.webp",
//"https://cdn.dummyjson.com/product-images/41/3.jpg",
//"https://cdn.dummyjson.com/product-images/41/4.jpg",
//"https://cdn.dummyjson.com/product-images/41/thumbnail.webp"

class Converter {
    @TypeConverter
    fun jsonFromImages(images: List<String>): String =
        Gson().toJson(images)
//        ProductEntity(
//            product.id, product.title, product.description, product.price,
//            product.discountPercentage, product.rating, product.category,
//            product.thumbnail, Gson().toJson(product.images)
//        )

    @TypeConverter
    fun imagesFromJson(json: String): List<String> =
        Gson().fromJson(
            json,
            object : TypeToken<List<String>>() {}.type
        )
//
//        return Product(
//            productEntity.id, productEntity.title, productEntity.description, productEntity.price,
//            productEntity.discountPercentage, productEntity.rating, productEntity.category,
//            productEntity.thumbnail, images
//        )
    }