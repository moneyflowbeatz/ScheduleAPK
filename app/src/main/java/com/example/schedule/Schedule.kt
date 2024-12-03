package com.example.schedule

data class Schedule(
    val id: Int,
    val groupId: Int,
    val group: String?,
    val subjectId: Int,
    val subject: String?,
    val teacherId: Int,
    val teacher: String?,
    val weekDay: String,
    val studyWeekId: Int,
    val studyWeek: String?,
    val scheduleStart: String,
    val scheduleEnd: String,
    val scheduleNumber: Int
)

data class Group(val id: Int, val name: String)
data class Subject(val id: Int, val name: String)
data class Teacher(val id: Int, val name: String)
