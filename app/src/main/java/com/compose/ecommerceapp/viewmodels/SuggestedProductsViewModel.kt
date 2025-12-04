// SuggestedProductsViewModel.kt
package com.compose.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.ecommerceapp.manager.RecentProductsManager
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestedProductsViewModel @Inject constructor(
    private val productRepository: FirestoreRepository,
    private val recentProductsManager: RecentProductsManager
) : ViewModel() {

    private val _suggestedProducts = MutableStateFlow<List<Product>>(emptyList())
    val suggestedProducts: StateFlow<List<Product>> = _suggestedProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadSuggestedProducts()
    }

    private fun loadSuggestedProducts() {
        viewModelScope.launch {
            _isLoading.value = true

            recentProductsManager.recentProductIds
                .collect { ids ->
                    if (ids.isEmpty()) {
                        _suggestedProducts.value = emptyList()
                        _isLoading.value = false
                        return@collect
                    }

                    // Most recent first
                    val recentFirstIds = ids.toList().reversed().take(15)

                    val products = recentFirstIds.mapNotNull { id ->
                        productRepository.getProductById(id)
                    }

                    _suggestedProducts.value = products
                    _isLoading.value = false
                }
        }
    }

    fun refresh() {
        loadSuggestedProducts()
    }
}