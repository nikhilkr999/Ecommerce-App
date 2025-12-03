package com.compose.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.model.WishlistProduct
import com.compose.ecommerceapp.repositories.CartRepository
import com.compose.ecommerceapp.repositories.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val cartRepository: CartRepository
): ViewModel() {

    val wishlistItem = wishlistRepository.wishlistItems

    fun addToWishList(product: Product){
        viewModelScope.launch {
            wishlistRepository.addToWishlist(product = product)
        }
    }

    fun removeFromWishList(product: WishlistProduct){
        viewModelScope.launch {
            wishlistRepository.removeFromWishlist(product)
        }
    }

//    fun addToCartAndRemoveFromWishlist(product: Product) {
//        viewModelScope.launch {
//            cartRepository.addToCart(product)
//            wishlistRepository.removeFromWishlist(product)
//        }
//    }

}