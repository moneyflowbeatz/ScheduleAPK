package com.example.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedule.api.ApiService

@Composable
fun ProfileScreen(
    studentInfo: StudentInfo,
    onSubscriptionToggle: (Boolean) -> Unit,
    isSubscribed: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ваш профиль", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Информация о пользователе")
        Text("ФИО: ${studentInfo.fullName}")
        Text("Группа: ${studentInfo.group}")
        Text("Классный руководитель: ${studentInfo.groupLeader}")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Подписка на уведомления")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isSubscribed,
                onCheckedChange = onSubscriptionToggle
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Получать уведомления")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Реализация смены пароля в будущем */ }) {
            Text("Сменить пароль")
        }
    }
}


@Composable
fun ProfileScreenWithData(userService: UserService, groupService: GroupService, token: String) {
    // Состояния для хранения информации о студенте и группе
    val studentInfo = remember { mutableStateOf<List<Student>?>(null) }
    val groupInfo = remember { mutableStateOf<List<Group>?>(null) }
    val isSubscribed = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Используем LaunchedEffect для загрузки данных
    LaunchedEffect(token) {
        // Получаем информацию о студенте и группе
        try {
            // Загрузка информации о студенте
            val studentResponse = userService.getStudent("Bearer $token")
            if (studentResponse.isSuccessful) {
                studentInfo.value = studentResponse.body() // Ожидается список студентов
            } else {
                errorMessage = "Ошибка загрузки данных о студенте: ${studentResponse.code()}"
            }

            // Загрузка информации о группе
            val groupResponse = groupService.getGroup("Bearer $token")
            if (groupResponse.isSuccessful) {
                groupInfo.value = groupResponse.body() // Ожидается список групп
            } else {
                errorMessage = "Ошибка загрузки данных о группе: ${groupResponse.code()}"
            }
        } catch (e: Exception) {
            errorMessage = "Ошибка при запросе данных: ${e.localizedMessage}"
        }
    }

    // Если данные загружаются, показываем сообщение
    if (studentInfo.value == null || groupInfo.value == null) {
        LoadingScreen()

        // Показать сообщение об ошибке
        errorMessage.takeIf { it.isNotEmpty() }?.let {
            Text("Ошибка: $it", color = MaterialTheme.colorScheme.error)
        }

        return
    }

    // Данные о студенте
    studentInfo.value?.let { studentList ->
        val student = studentList.firstOrNull() // Получаем первого студента
        student?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Ваш профиль", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Информация о пользователе")
                Text("ФИО: ${it.fio}")
                Text("Группа: ${it.groupId}") // Группа (ID)

                Spacer(modifier = Modifier.height(16.dp))

                // Информация о группе
                groupInfo.value?.let { groupList ->
                    val group = groupList.firstOrNull() // Получаем первую группу
                    group?.let {
                        Text("Группа: ${it.name}")
                        Text("Классный руководитель: ${it.teacherId}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Подписка на уведомления
                Text("Подписка на уведомления")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isSubscribed.value,
                        onCheckedChange = { isSubscribed.value = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Получать уведомления")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка смены пароля
                Button(onClick = { /* Реализация смены пароля */ }) {
                    Text("Сменить пароль")
                }
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()  // Показываем индикатор загрузки
    }
}

