package com.compose.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel() {

    private val TAG = "ProductDetailsViewModel"
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun fetchProduct(productId: String){
        viewModelScope.launch {
            try {
                val product = repository.getProductById(productId)
                _product.value = product
                Log.d(TAG," product details fetched of id ${productId}")
            }catch (e: Exception){
                Log.d(TAG,"error fetching product details of id ${productId} ${e.message}")
            }

        }
    }
}