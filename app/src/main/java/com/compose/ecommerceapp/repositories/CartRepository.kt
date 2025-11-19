package com.compose.ecommerceapp.repositories

import android.util.Log
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.room.CartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    val getAllCartItems : Flow<List<Product>> = cartDao.getAllCartItem()

    suspend fun addToCart(product: Product){
        val existingItem = cartDao.getCartItemById(product.id)

        if(existingItem!=null){
            Log.d("CartRepository", "item is already added")
            cartDao.updateCartItem(product)
        }else{
            cartDao.insertCartItem(product)
            Log.d("CartRepository", "item added into cart")
        }
    }

    suspend fun removeCartItem(product: Product){
        cartDao.deleteCartItem(product)
        Log.d("CartRepository", "item removed from cart")
    }

    suspend fun clearCart(){
        cartDao.clearCart()
        Log.d("CartRepository", "cart cleared")
    }
}