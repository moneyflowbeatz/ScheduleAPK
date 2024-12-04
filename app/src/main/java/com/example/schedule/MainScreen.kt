package com.example.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.schedule.api.ApiService

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(token: String) {
    var selectedTab by remember { mutableStateOf(0) }

    // Получаем доступ к сервисам из ApiClient
    val userService = ApiClient.userService
    val groupService = ApiClient.groupService

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> ScheduleScreen(token = token)  // Главная (расписание на текущий день)
                1 -> CalendarScreen(token = token) // Календарь
                2 -> ProfileScreenWithData(userService = userService, groupService = groupService, token = token)  // Профиль
            }
        }
    }
}

