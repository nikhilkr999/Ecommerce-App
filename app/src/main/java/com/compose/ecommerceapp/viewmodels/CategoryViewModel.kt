package com.compose.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.ecommerceapp.model.Category
import com.compose.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel(){

    private val TAG = "CategoryViewModel"

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        Log.d(TAG, "Initializing CategoryViewModel") // LOG 1
        fetchCategories()
    }

    private fun fetchCategories(){
        Log.d(TAG, "Calling fetchCategories()") // LOG 2

        viewModelScope.launch {
            repository.getCategoriesFlow()
                .catch {
                    Log.e(TAG, "Error receiving categories flow: ${it.message}") // LOG 3
                }
                .collect { categories ->
                    Log.d(TAG, "Flow emitted ${categories.size} categories") // LOG 4
                    _categories.value = categories
                }
        }
    }
}
