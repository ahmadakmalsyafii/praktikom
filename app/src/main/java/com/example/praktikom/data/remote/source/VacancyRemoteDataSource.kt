package com.example.praktikom.data.remote.source

import com.example.praktikom.data.remote.dto.RegistrationDto
import com.example.praktikom.data.remote.dto.RegistrationInsertDto
import com.example.praktikom.data.remote.dto.VacancyDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import javax.inject.Inject

interface VacancyRemoteDataSource {
    suspend fun getVacancies(): List<VacancyDto>
    suspend fun getVacancyDetail(id: Int): VacancyDto
    suspend fun getRegistrations(): List<RegistrationDto>
    suspend fun uploadTranskrip(fileName: String, fileBytes: ByteArray): String
    suspend fun insertRegistration(registration: RegistrationInsertDto)
}

class VacancyRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : VacancyRemoteDataSource {

    override suspend fun getVacancies(): List<VacancyDto> {
        return supabaseClient.postgrest["practicum_vacancies"]
            .select(Columns.raw("*, courses(*)"))
            .decodeList<VacancyDto>()
    }

    override suspend fun getVacancyDetail(id: Int): VacancyDto {
        return supabaseClient.postgrest["practicum_vacancies"]
            .select(Columns.raw("*, courses(*)")) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<VacancyDto>()
    }

    override suspend fun getRegistrations(): List<RegistrationDto> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
            ?: throw Exception("User tidak login")

        return supabaseClient.postgrest["registrations"]
            .select(Columns.raw("*, practicum_vacancies(*, courses(*))")) {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<RegistrationDto>()
    }

    override suspend fun uploadTranskrip(fileName: String, fileBytes: ByteArray): String {
        val bucket = supabaseClient.storage.from("transkrip")
        bucket.upload(fileName, fileBytes)
        return "transkrip/$fileName"
    }

    override suspend fun insertRegistration(registration: RegistrationInsertDto) {
        supabaseClient.postgrest["registrations"].insert(registration)
    }
}
