package com.example.algorithmic_pasticceria.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import com.example.algorithmic_pasticceria.generated.resources.Res
import com.example.algorithmic_pasticceria.generated.resources.noto_color_emoji

/**
 * Custom font family loading the bundled Noto Color Emoji font
 * to ensure emojis render on Skia Web (Wasm/JS) and Desktop.
 */
@Composable
fun getEmojiFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.noto_color_emoji)
    )
}