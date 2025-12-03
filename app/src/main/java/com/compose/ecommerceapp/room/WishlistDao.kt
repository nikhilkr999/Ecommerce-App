package com.compose.ecommerceapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.model.WishlistProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(product: WishlistProduct)

    @Delete
    suspend fun removeFromWishlist(product: WishlistProduct)

    @Query("SELECT * FROM wishlist_items")
    fun getAllWishlistItems(): Flow<List<WishlistProduct>>

    @Query("DELETE FROM wishlist_items")
    suspend fun clearWishlist()

    @Query("SELECT * FROM wishlist_items WHERE id = :productId")
    suspend fun getWishlistItemById(productId: String): WishlistProduct?
}