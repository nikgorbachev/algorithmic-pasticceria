package com.example.algorithmic_pasticceria.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AnimalType(val label: String, val bg: Color, val accent: Color) {
    CAT("CAT", Color(0xFFFFD166), Color(0xFFE09F3E)),
    DOG("DOG", Color(0xFFFFB4A2), Color(0xFFE56B6F)),
    FOX("FOX", Color(0xFFF4A261), Color(0xFFE76F51)),
    BEAR("BEAR", Color(0xFFB5E48C), Color(0xFF76C893))
}

@Composable
fun AnimalAvatar(
    type: AnimalType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(type.bg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = type.label,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                color = Color(0xFF2B1704),
                letterSpacing = 1.sp
            )
        }
    }
}