package com.compose.ecommerceapp.repositories

import android.util.Log
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.model.WishlistProduct
import com.compose.ecommerceapp.model.toWishlistProduct
import com.compose.ecommerceapp.room.WishlistDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepository @Inject constructor(
    private val wishlistDao: WishlistDao
) {
    val wishlistItems: Flow<List<WishlistProduct>> = wishlistDao.getAllWishlistItems()

    suspend fun addToWishlist(product: Product) {
        val wishlistProduct = product.toWishlistProduct()
        wishlistDao.addToWishlist(wishlistProduct)
        Log.d("WishlistRepository", "Item added to wishlist")
    }

    suspend fun removeFromWishlist(wishlistProduct: WishlistProduct) {
        wishlistDao.removeFromWishlist(wishlistProduct)
        Log.d("WishlistRepository", "Item removed from wishlist")
    }

    suspend fun clearWishlist() {
        wishlistDao.clearWishlist()
        Log.d("WishlistRepository", "Wishlist cleared")
    }

    suspend fun getItemById(productId: String): WishlistProduct? {
        return wishlistDao.getWishlistItemById(productId)
    }

}