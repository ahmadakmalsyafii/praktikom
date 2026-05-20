package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(): Result<List<Schedule>> {
        return repository.getSchedule();
    }
}