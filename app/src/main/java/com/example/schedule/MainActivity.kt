package com.example.schedule

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.example.schedule.ui.theme.ScheduleTheme

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var token by remember { mutableStateOf("") }

            if (token.isEmpty()) {
                LoginScreen { newToken ->
                    token = newToken  // Сохраняем токен
                }
            } else {
                // Если есть токен, показываем расписание
                ScheduleScreen(token = token)  // Передаем токен
            }
        }
    }
}



