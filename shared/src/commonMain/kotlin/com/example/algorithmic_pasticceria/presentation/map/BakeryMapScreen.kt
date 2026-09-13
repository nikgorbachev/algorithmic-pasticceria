package com.example.algorithmic_pasticceria.presentation.map

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithmic_pasticceria.presentation.navigation.Screen

@Composable
fun BakeryMapScreen(onNavigateTo: (Screen) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF4E8)), // Warm Italian bakery cream
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp).align(Alignment.TopCenter)
        ) {
            Text(
                text = "🥐 Algorithmic Pastry Shop",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF4A2810),
                modifier = Modifier.padding(top = 24.dp)
            )
            Text(
                text = "Select a station to train your algorithms",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7A5C43)
            )
        }

        // 2.5D Isometric Surface
        Box(
            modifier = Modifier
                .size(520.dp)
                .graphicsLayer {
                    rotationX = 55f
                    rotationZ = -45f
                    cameraDistance = 16f
                }
                .shadow(16.dp, shape = RoundedCornerShape(24.dp))
                .background(Color(0xFFE8D5B7), shape = RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            // Customer Queue (Top Left) -> Routes to the Chapter Table of Contents
            BakeryStation(
                title = "Counter Line 🐱",
                subtitle = "Queue & Sorting",
                color = Color(0xFFFFB4A2),
                modifier = Modifier.align(Alignment.TopStart),
                onClick = { onNavigateTo(Screen.SortingChapterMenu) }
            )

            // Cash Register (Top Right)
            BakeryStation(
                title = "La Cassa 💰",
                subtitle = "Greedy & DP",
                color = Color(0xFFB5E48C),
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { onNavigateTo(Screen.CashRegister) }
            )

            // Display Case (Bottom Left)
            BakeryStation(
                title = "Vetrina 🍰",
                subtitle = "Binary Search",
                color = Color(0xFF90E0EF),
                modifier = Modifier.align(Alignment.BottomStart),
                onClick = { onNavigateTo(Screen.PastryShowcase) }
            )

            // Espresso Machine (Bottom Right)
            BakeryStation(
                title = "Bancone ☕",
                subtitle = "Hash Tables & LRU",
                color = Color(0xFFFFD166),
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { /* Will open Bancone */ }
            )
        }
    }
}

@Composable
private fun BakeryStation(
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 12f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation.dp),
        modifier = modifier
            .size(190.dp, 100.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2B1704))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF5E3C1B))
        }
    }
}