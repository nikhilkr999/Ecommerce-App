package com.compose.ecommerceapp.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.compose.ecommerceapp.compose.AddToCartButton
import com.compose.ecommerceapp.manager.RecentProductsManager
import com.compose.ecommerceapp.screens.ads.NativeAdComposable
import com.compose.ecommerceapp.util.WishlistButton
import com.compose.ecommerceapp.viewmodels.CartViewModel
import com.compose.ecommerceapp.viewmodels.ProductDetailsViewModel
import com.compose.ecommerceapp.viewmodels.WishlistViewModel

@Composable
fun ProductDetailsScreen(
    productId: String,
    productViewModel: ProductDetailsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    val isInCart = cartItems.any { it.id == productId }

    val wishlistItems by wishlistViewModel.wishlistItem.collectAsState(initial = emptyList())
    val isWishlisted = wishlistItems.any { it.id == productId }

    val recentProductsManager = RecentProductsManager(LocalContext.current)

    LaunchedEffect(productId) {
        productViewModel.fetchProduct(productId)
    }
    LaunchedEffect(productId) {
        recentProductsManager.addProduct(productId = productId)
    }
    
    val productState = productViewModel.product.collectAsState()
    val product = productState.value

    if (product == null) {
        Text("product not found")
    } else {

        // ROOT — must be Box to allow .align()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).statusBarsPadding()
        ) {

            // ----------------- MAIN CONTENT -----------------
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            ) {

                Image(
                    painter = rememberAsyncImagePainter(product.imageUrl),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))
                val rupee = "\u20B9"
                Text(
                    text = "${rupee }${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = product.desc ?: "No description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                NativeAdComposable()
            }

            Column(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                AddToCartButton(
                    isAdded = isInCart,
                    onClick = { cartViewModel.addToCart(product) },
                    modifier = Modifier.padding(4.dp)
                )

                // Add to Wishlist
                WishlistButton(
                    isWishlisted = isWishlisted,
                    onClick = { wishlistViewModel.addToWishList(product) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
