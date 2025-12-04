package com.compose.ecommerceapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compose.ecommerceapp.ui.theme.PrimaryColor

@Composable
fun SectionTitle(
    title: String,
    actionText: String? = null,           // Now optional
    onActionClick: (() -> Unit)? = null    // Now optional
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Only show "See All" if actionText is provided
        if (actionText != null && onActionClick != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { onActionClick() }
                    .padding(8.dp) // Better tap area
            )
        }
    }
}