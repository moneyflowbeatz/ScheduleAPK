package com.example.schedule

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://10.0.2.2:5058/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Тайм-аут подключения
        .readTimeout(30, TimeUnit.SECONDS)     // Тайм-аут чтения
        .writeTimeout(30, TimeUnit.SECONDS)    // Тайм-аут записи
        .sslSocketFactory(UnsafeOkHttpClient.getUnsafeSslSocketFactory(), UnsafeOkHttpClient.getUnsafeTrustManager())  // Используем небезопасное соединение
        .hostnameVerifier { _, _ -> true }     // Отключаем проверку имени хоста
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    // Инициализация в ApiClient
    val scheduleService: ScheduleService by lazy {
        retrofit.create(ScheduleService::class.java)
    }
}
