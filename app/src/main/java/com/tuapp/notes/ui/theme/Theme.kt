package com.tuapp.notes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// COLORES PARA MODO CLARO
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),        // Azul
    onPrimary = Color.White,            // Texto blanco sobre azul
    secondary = Color(0xFF9C27B0),      // Púrpura
    onSecondary = Color.White,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

// COLORES PARA MODO OSCURO
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),        // Azul claro
    onPrimary = Color(0xFF003258),
    secondary = Color(0xFFCE93D8),      // Púrpura claro
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
)

/**
 * Tema principal de la app.
 */
@Composable
fun NotesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Esta variable viene de Type.kt
        content = content
    )
}