package com.example.praktikom.data.repository

import com.example.praktikom.data.remote.dto.RegistrationInsertDto
import com.example.praktikom.data.remote.dto.toDomain
import com.example.praktikom.data.remote.source.VacancyRemoteDataSource
import com.example.praktikom.domain.model.Registration
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.repository.VacancyRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

class VacancyRepositoryImpl @Inject constructor(
    private val remoteDataSource: VacancyRemoteDataSource,
    private val supabaseClient: SupabaseClient
) : VacancyRepository {

    override suspend fun getVacancies(): Result<List<Vacancy>> {
        return try {
            val list = remoteDataSource.getVacancies().map { it.toDomain() }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getVacancyDetail(id: Int): Result<Vacancy> {
        return try {
            val vacancy = remoteDataSource.getVacancyDetail(id).toDomain()
            Result.success(vacancy)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRegistrations(): Result<List<Registration>> {
        return try {
            val list = remoteDataSource.getRegistrations().map { it.toDomain() }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun applyVacancy(
        vacancyId: Int,
        grade: String,
        fileBytes: ByteArray,
        fileName: String
    ): Result<Unit> {
        return try {
            val userId = supabaseClient.auth.currentUserOrNull()?.id
                ?: throw Exception("User tidak sedang login")

            val transkripUrl = remoteDataSource.uploadTranskrip(fileName, fileBytes)

            val insertDto = RegistrationInsertDto(
                userId = userId,
                vacancyId = vacancyId,
                nilaiMkSyarat = grade,
                transkripUrl = transkripUrl,
                status = "pending"
            )

            remoteDataSource.insertRegistration(insertDto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
