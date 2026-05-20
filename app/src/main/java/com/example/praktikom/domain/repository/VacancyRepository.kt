package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Registration
import com.example.praktikom.domain.model.Vacancy

interface VacancyRepository {
    suspend fun getVacancies(): Result<List<Vacancy>>
    suspend fun getVacancyDetail(id: Int): Result<Vacancy>
    suspend fun getRegistrations(): Result<List<Registration>>
    suspend fun applyVacancy(vacancyId: Int, grade: String, fileBytes: ByteArray, fileName: String): Result<Unit>
}
