package com.compose.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel(){
    private val TAG = "ProductViewModel"
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    fun fetchProducts(categoryId: String){
      viewModelScope.launch {
          try {
              val products = repository.getProductByCategory(categoryId)
              _products.value = products
              Log.d(TAG, "products of category ${categoryId} fetched ")
          }catch (e: Exception){
              Log.d(TAG, "error fetching produts ${e.message}")
          }

      }
    }

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    fun getAllProductsInFireStore(){
        viewModelScope.launch {
            try {
                val allProducts = repository.getAllProductsInFirestore()
                _allProducts.value = allProducts
                Log.d(TAG,"All products fetched from firestore")
            }catch (e: Exception){
                Log.d(TAG, "error fetching all products ${e.message}")
            }
        }
    }
}