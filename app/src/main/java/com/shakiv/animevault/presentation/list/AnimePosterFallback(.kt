package com.shakiv.animevault.presentation.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimePosterFallback(
    title: String?,
    modifier: Modifier = Modifier
) {
    // 1. Get the first letter (e.g., "Bleach..." -> "B")
    val initial = title?.firstOrNull()?.uppercase() ?: "?"

    // 2. Create the container
    // We use a Surface/Box to mimic the physical shape of a poster
    Surface(
        modifier = modifier, // Dimensions passed from parent
        color = MaterialTheme.colorScheme.primaryContainer, // Or a random color
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = initial,
                style = MaterialTheme.typography.displayMedium, // Large, bold text
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}