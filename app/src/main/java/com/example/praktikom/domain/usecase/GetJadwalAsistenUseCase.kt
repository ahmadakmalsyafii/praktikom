package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.repository.ClassRepository
import javax.inject.Inject

class GetJadwalAsistenUseCase @Inject constructor(
    private val repository: ClassRepository
) {
    suspend operator fun invoke(): Result<List<Schedule>> {
        return repository.getClassesByAssistant()
    }
}
