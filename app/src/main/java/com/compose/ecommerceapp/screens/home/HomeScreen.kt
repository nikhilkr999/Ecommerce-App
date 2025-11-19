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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.viewmodels.CategoryViewModel
import com.compose.ecommerceapp.viewmodels.ProductViewModel
import com.compose.ecommerceapp.viewmodels.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    onProfileCLick: ()-> Unit,
    onCartClick: ()-> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
){
    Scaffold (
        topBar = {MyTopAppBar(onProfileCLick, onCartClick)},
        bottomBar = {BottomNavigationBar(navController)}
    ){paddingValues ->
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ){
            //Search section
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery.value,
                onQueryChange = {searchQuery.value = it},
                onSearch = {
                    searchViewModel.searchProducts(searchQuery.value)
                    focusManager.clearFocus()
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            //Search result section
            if(searchQuery.value.isNotBlank()){
                SearchResultSection(navController)
            }

            //Category section
            SectionTitle("Categories","See All") {
                navController.navigate(Screens.CategoryList.route)
            }

            //Mock the categories
            val categorieState = categoryViewModel.categories.collectAsState()
            val categories = categorieState.value

            val selectedCategory = remember {mutableStateOf(0)}

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size){
                    CategoryChip(
                        icon = categories[it].iconUrl,
                        text = categories[it].name,
                        isSelected = selectedCategory.value==null,
                        onClick = {
                            selectedCategory.value=it
                            navController.navigate(
                                Screens.ProductList.createRoute(categories[it].id.toString(), categories[it].name.toString())
                            )
                        }
                    )

                }
            }

            LaunchedEffect(Unit) {
                productViewModel.getAllProductsInFireStore()
            }
           // productViewModel.getAllProductsInFireStore()
            val allProductsState = productViewModel.allProducts.collectAsState()
            val allProductsFound = allProductsState.value

            Spacer(modifier = Modifier.height(16.dp))


            //Featured product section
            SectionTitle("Featured","See All") {
                navController.navigate(
                    Screens.CategoryList.route
                )
            }
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allProductsFound){ product->
                    FeatureProductCard(product) {
                        navController.navigate(
                            Screens.ProductDetails.createRoute(product.id)
                        )
                    }
                }
            }
        }
    }
}