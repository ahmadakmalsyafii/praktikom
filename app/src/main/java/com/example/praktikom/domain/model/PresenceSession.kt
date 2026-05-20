package com.example.praktikom.domain.model

data class PresenceSession(
    val id: Int,
    val classId: Int,
    val topic: String,
    val date: String,
    val startTime: String,
    val endTime: String
)
