package com.example.schedule

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(token: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val schedules = remember { mutableStateListOf<Schedule>() }
    val currentDay = getCurrentDay()
    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    // Загрузка данных с API
    LaunchedEffect(Unit) {
        try {
            val response = scheduleService.getSchedules(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    schedules.clear()
                    schedules.addAll(it)

                    // Фильтрация по текущему дню
                    val filteredSchedules = schedules.filter { it.weekDay == currentDay }
                    schedules.clear()
                    if (filteredSchedules.isNotEmpty()) {
                        schedules.addAll(filteredSchedules)
                    } else {
                        // Если нет расписания на текущий день, выводим понедельник
                        schedules.addAll(it.filter { it.weekDay == "Понедельник" })
                    }
                }
            } else {
                Toast.makeText(context, "Не удалось загрузить расписание", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Отображение текущего дня недели и даты
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Сегодня: $currentDay, ${currentDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}", style = MaterialTheme.typography.bodyLarge)

        // Количество пар
        val totalClasses = schedules.distinctBy { it.scheduleNumber }.size
        Text("Количество пар: $totalClasses", style = MaterialTheme.typography.bodyMedium)

        // Следующая пара
        val nextClass = schedules.filter { parseTime(it.scheduleStart).isAfter(currentTime) }.minByOrNull { parseTime(it.scheduleStart) }
        nextClass?.let {
            Text("Следующая пара: ${it.subject ?: "Неизвестный предмет"} в ${parseTime(it.scheduleStart)}", style = MaterialTheme.typography.bodyMedium)
        } ?: Text("Следующая пара: нет", style = MaterialTheme.typography.bodyMedium)

        // Время до следующей пары
        nextClass?.let {
            val timeUntilNextClass = java.time.Duration.between(currentTime, parseTime(it.scheduleStart)).toMinutes()
            Text("До следующей пары: $timeUntilNextClass минут", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение списка расписания
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(schedules.sortedBy { parseTime(it.scheduleStart) }) { schedule ->
                ScheduleRow(schedule)
            }
        }
    }
}

// Функция для получения текущего дня недели на русском языке
@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDay(): String {
    val dayOfWeek = LocalDate.now().dayOfWeek // Получаем текущий день недели
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "Понедельник"
        DayOfWeek.TUESDAY -> "Вторник"
        DayOfWeek.WEDNESDAY -> "Среда"
        DayOfWeek.THURSDAY -> "Четверг"
        DayOfWeek.FRIDAY -> "Пятница"
        DayOfWeek.SATURDAY -> "Суббота"
        DayOfWeek.SUNDAY -> "Воскресенье"
    }
}

// Функция для преобразования строки времени в LocalTime
@RequiresApi(Build.VERSION_CODES.O)
fun parseTime(timeString: String): LocalTime {
    return LocalTime.parse(timeString)
}