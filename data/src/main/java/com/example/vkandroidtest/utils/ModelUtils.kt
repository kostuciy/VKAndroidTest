package com.example.vkandroidtest.utils

import com.example.vkandroidtest.db.entity.ProductEntity
import com.example.vkandroidtest.model.Product

class ModelUtils {

    companion object {

        fun toEntity(product: Product): ProductEntity =
            ProductEntity(
                product.id, product.title, product.description,
                product.price, product.discountPercentage, product.stock,
                product.rating, product.category, product.thumbnail, product.images
            )

        fun toModel(productEntity: ProductEntity): Product =
            Product(
                productEntity.id, productEntity.title, productEntity.description,
                productEntity.price, productEntity.discountPercentage, productEntity.stock,
                productEntity.rating, productEntity.category, productEntity.thumbnail, productEntity.images
            )
    }
}