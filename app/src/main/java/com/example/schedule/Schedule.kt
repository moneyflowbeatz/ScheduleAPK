package com.example.schedule

data class Schedule(
    val id: Int,
    val groupName: String,   // Изменено на groupName
    val subjectName: String, // Изменено на subjectName
    val teacherFIO: String,  // Изменено на teacherFIO
    val weekDay: String,
    val studyWeekId: Int,
    val scheludeNumber: Int,
    val scheduleStart: String,
    val scheduleEnd: String
)

data class Group(val id: Int, val name: String)
data class Subject(val id: Int, val name: String)
data class Teacher(val id: Int, val name: String)
