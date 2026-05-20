package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.repository.ClassRepository
import javax.inject.Inject

class CreatePresenceSessionUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(
        classId: Int,
        pertemuanKe: Int,
        tanggal: String,
        jamBuka: String,
        jamTutup: String
    ): Result<Unit> {
        return classRepository.createPresenceSession(
            classId = classId,
            pertemuanKe = pertemuanKe,
            tanggal = tanggal,
            jamBuka = jamBuka,
            jamTutup = jamTutup
        )
    }
}
