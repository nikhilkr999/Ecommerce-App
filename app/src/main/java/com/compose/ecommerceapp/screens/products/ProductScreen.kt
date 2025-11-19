package com.compose.ecommerceapp.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.viewmodels.CartViewModel
import com.compose.ecommerceapp.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    categoryId: String,
    categoryName: String?,
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
){

    LaunchedEffect(categoryId) {
        productViewModel.fetchProducts(categoryId)
    }

    val productSate = productViewModel.products.collectAsState()
    val products = productSate.value

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        Text(
            text = categoryName?:"Products",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp))

        if (products.isEmpty()){
            Text(text = "No products found", Modifier.padding(16.dp))
        }else{
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {

                items(products){ product->
                    ProductItem(
                        product = product,
                        onClick = {
                            navController.navigate(
                                Screens.ProductDetails.createRoute(product.id)
                            )
                                  },
                        onAddToCart = {
                            cartViewModel.addToCart(product)
                        }
                    )
                }

            }
        }
    }
}