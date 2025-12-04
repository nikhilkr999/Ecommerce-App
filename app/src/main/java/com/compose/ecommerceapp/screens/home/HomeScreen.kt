package com.compose.ecommerceapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.viewmodels.CategoryViewModel
import com.compose.ecommerceapp.viewmodels.ProductViewModel
import com.compose.ecommerceapp.viewmodels.SearchViewModel
import com.compose.ecommerceapp.viewmodels.SuggestedProductsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    onProfileCLick: () -> Unit,
    onCartClick: () -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    suggestedViewModel: SuggestedProductsViewModel = hiltViewModel() // Added
) {
    Scaffold(
        topBar = { MyTopAppBar(onProfileCLick, onCartClick) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // === Search Section ===
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current

            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = {
                    searchViewModel.searchProducts(searchQuery.value)
                    focusManager.clearFocus()
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            // Show search results only when query is not empty
            if (searchQuery.value.isNotBlank()) {
                SearchResultSection(navController)
            }

            // === Categories ===
            SectionTitle("Categories", "See All") {
                navController.navigate(Screens.CategoryList.route)
            }

            val categories by categoryViewModel.categories.collectAsState()
            val selectedCategory = remember { mutableStateOf<Int?>(null) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryChip(
                        icon = category.iconUrl,
                        text = category.name,
                        isSelected = selectedCategory.value == index,
                        onClick = {
                            selectedCategory.value = index
                            navController.navigate(
                                Screens.ProductList.createRoute(category.id.toString(), category.name)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // === Load All Products for Featured ===
            LaunchedEffect(Unit) {
                productViewModel.getAllProductsInFireStore()
            }
            val allProducts by productViewModel.allProducts.collectAsState()

            // === Featured Products ===
            SectionTitle("Featured", "See All") {
                navController.navigate(Screens.CategoryList.route)
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allProducts, key = { it.id }) { product ->
                    FeatureProductCard(product) {
                        navController.navigate(Screens.ProductDetails.createRoute(product.id))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === SUGGESTED FOR YOU SECTION ===
            val suggestedProducts by suggestedViewModel.suggestedProducts.collectAsState()
            val isLoadingSuggested by suggestedViewModel.isLoading.collectAsState()

            if (suggestedProducts.isNotEmpty()) {
                SectionTitle(title = "Suggested for You", actionText = null, onActionClick = null)

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(suggestedProducts, key = { it.id }) { product ->
                        FeatureProductCard(product) {
                            navController.navigate(Screens.ProductDetails.createRoute(product.id))
                        }
                    }
                }
            } else if (!isLoadingSuggested) {
                Text(
                    text = "Start browsing products to see personalized suggestions!",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}