package com.example.schedule.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.schedule.ScheduleScreen

@Composable
fun ScheduleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        ),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScheduleTheme {
        // Пример контента с темой
        ScheduleScreen(token = "sampleToken123")
    }
}
