package com.compose.ecommerceapp.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WishlistButton(
    isWishlisted: Boolean,         // state to show animation
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        enabled = !isWishlisted, // disable after wishlisted (optional)
        modifier = modifier
            .background(
                color = if (isWishlisted) Color(0xFFE91E63) else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        AnimatedContent(
            targetState = isWishlisted,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "wishlist_animation"
        ) { wishlisted ->
            if (wishlisted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Added to Wishlist",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add to Wishlist",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
