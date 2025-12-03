package com.compose.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class Product(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val categoryId: String = "",
    val desc: String = ""
)

fun Product.toWishlistProduct() = WishlistProduct(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    categoryId = categoryId,
    desc = desc
)

fun WishlistProduct.toProduct() = Product(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    categoryId = categoryId,
    desc = desc
)
