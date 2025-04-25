package com.benedetto.modernandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//✅ Task: Try using Material 3 components like ElevatedButton, OutlinedCard, etc.
@Composable
internal fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFF121212)
        ),
        typography = Typography,
        content = content
    )
}
