package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Schedule

interface ScheduleRepository {
        suspend fun getSchedule(): Result<List<Schedule>>
}