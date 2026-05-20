package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.repository.VacancyRepository
import javax.inject.Inject

class ApplyVacancyUseCase @Inject constructor(
    private val vacancyRepository: VacancyRepository
) {
    suspend operator fun invoke(vacancyId: Int, grade: String, fileBytes: ByteArray, fileName: String): Result<Unit> {
        return vacancyRepository.applyVacancy(vacancyId, grade, fileBytes, fileName)
    }
}
