// GeneralScheduleScreen.kt
package com.example.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneralScheduleScreen(
    scheduleData: List<Schedule>,
    searchText: String,
    onSearch: (String) -> Unit,
    onSearchTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Поле для поиска
        TextField(
            value = searchText,
            onValueChange = {
                onSearchTextChange(it)
                onSearch(it) // Вызов поиска при изменении текста
            },
            label = { Text("Поиск по дисциплине, кабинету, группе, преподавателю") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Выводим отфильтрованное расписание
        LazyColumn {
            items(scheduleData) { schedule ->
                ScheduleItem(schedule)
            }
        }
    }
}


@Composable
fun ScheduleItem(schedule: Schedule) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text("Дисциплина: ${schedule.subjectName}")
        Text("Кабинет: ${schedule.scheludeNumber}") // Если кабинет - это номер расписания
        Text("Группа: ${schedule.groupName}")
        Text("Преподаватель: ${schedule.teacherFIO}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}
