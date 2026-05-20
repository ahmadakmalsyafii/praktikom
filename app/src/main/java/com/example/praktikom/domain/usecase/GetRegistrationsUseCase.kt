package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Registration
import com.example.praktikom.domain.repository.VacancyRepository
import javax.inject.Inject

class GetRegistrationsUseCase @Inject constructor(
    private val vacancyRepository: VacancyRepository
) {
    suspend operator fun invoke(): Result<List<Registration>> {
        return vacancyRepository.getRegistrations()
    }
}
