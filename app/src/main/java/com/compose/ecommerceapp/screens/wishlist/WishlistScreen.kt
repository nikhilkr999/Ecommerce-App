package com.compose.ecommerceapp.screens.wishlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.ecommerceapp.model.toProduct
import com.compose.ecommerceapp.screens.navigation.Screens
import com.compose.ecommerceapp.viewmodels.CartViewModel
import com.compose.ecommerceapp.viewmodels.WishlistViewModel

@Composable
fun WishlistScreen(
    navController: NavController,
    viewModel: WishlistViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val wishlistItems = viewModel.wishlistItem.collectAsState(initial = emptyList()).value
    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "My Wishlist",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (wishlistItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your wishlist is empty", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(wishlistItems) { product ->
                    val isInCart = cartItems.any { it.id == product.id }
                    WishlistItemCard(
                        isInCart = isInCart,
                        product = product,
                        onRemove = { viewModel.removeFromWishList (product) },
                        onAddToCart = { cartViewModel.addToCart(product.toProduct()) },
                        onItemClicked = {
                            navController.navigate(
                                Screens.ProductDetails.createRoute(product.id)
                            )
                        }
                    )
                }
            }
        }
    }
}