package com.compose.ecommerceapp.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.ecommerceapp.model.Product
import com.compose.ecommerceapp.viewmodels.CartViewModel

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val cartItemsState = cartViewModel.cartItems.collectAsState(initial = emptyList())
    val cartItems = cartItemsState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(
            text = "Your cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "Continue Shopping")
                }
            }
        } else {
            // Cart Items
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Take remaining space
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemoveItem = {
                            cartViewModel.removeFromCart(item)
                        }
                    )
                }
            }

            // Total and checkout section — now inside the same Column
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp)
                    )

                    Text(
                        text = "₹${cartViewModel.calculateTotal(cartItems)}",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        /**
                        paypal,
                        stripe,
                        mastercard sdk
                        **/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Proceed to checkout")
                }
            }
        }
    }
}
