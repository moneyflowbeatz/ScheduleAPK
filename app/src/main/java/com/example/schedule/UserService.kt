package com.example.schedule

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response

interface UserService {

    @GET("User/Login")
    suspend fun loginUser(
        @Query("Login") login: String,
        @Query("Password") password: String
    ): Response<String>

    @GET("User")
    suspend fun getUsers(): List<User>

    @POST("User")
    suspend fun createUser(@Body request: CreateUserRequest): User

    @GET("Schedule")
    suspend fun getSchedule(@Body request: ScheduleRequest): Response<List<ScheduleResponse>>

    data class ScheduleRequest(
        val groupId: Int,
        val subjectId: Int,
        val teacherId: Int,
        val weekDay: String,
        val studyWeekId: Int,
        val scheludeNumber: Int
    )

    data class ScheduleResponse(
        val groupName: String,
        val subjectName: String,
        val teacherName: String,
        val weekDay: String,
        val scheduleNumber: Int,
        val time: String
    )

    @GET("Groups")
    suspend fun getGroups(): Response<List<Group>>

    @GET("Subjects")
    suspend fun getSubjects(): Response<List<Subject>>

    @GET("Teachers")
    suspend fun getTeachers(): Response<List<Teacher>>


}