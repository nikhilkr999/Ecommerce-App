// File: compose/AddToCartButton.kt
package com.compose.ecommerceapp.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddToCartButton(
    isAdded: Boolean,           // This controls whether to show tick or cart
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        enabled = !isAdded,  // Disable button after added (optional but recommended)
        modifier = modifier
            .background(
                color = if (isAdded) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        AnimatedContent(
            targetState = isAdded,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "cart_check_animation"
        ) { added ->
            if (added) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Added to cart",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to cart",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}