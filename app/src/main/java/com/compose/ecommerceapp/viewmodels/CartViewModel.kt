package com.compose.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val cartItems = repository.getAllCartItems

    fun addToCart(product: Product){
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun removeFromCart(product: Product){
        viewModelScope.launch {
            repository.removeCartItem(product)
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun calculateTotal(items: List<Product>): Double{
        return items.sumOf { it.price }
    }


}