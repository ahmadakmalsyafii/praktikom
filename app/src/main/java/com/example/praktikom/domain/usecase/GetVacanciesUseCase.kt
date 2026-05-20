package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.repository.VacancyRepository
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val vacancyRepository: VacancyRepository
) {
    suspend operator fun invoke(): Result<List<Vacancy>> {
        return vacancyRepository.getVacancies()
    }
}
