package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.Registration
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDto(
    val id: Int,
    @SerialName("user_id") val userId: String,
    @SerialName("vacancy_id") val vacancyId: Int,
    @SerialName("nilai_mk_syarat") val nilaiMkSyarat: String,
    @SerialName("transkrip_url") val transkripUrl: String,
    val status: String,
    @SerialName("catatan_reviewer") val catatanReviewer: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("reviewed_at") val reviewedAt: String? = null,
    @SerialName("reviewed_by") val reviewedBy: String? = null,
    @SerialName("practicum_vacancies") val practicumVacancies: VacancyDto? = null
)

fun RegistrationDto.toDomain() = Registration(
    id = id,
    userId = userId,
    vacancyId = vacancyId,
    nilaiMkSyarat = nilaiMkSyarat,
    transkripUrl = transkripUrl,
    status = status,
    catatanReviewer = catatanReviewer,
    createdAt = createdAt,
    reviewedAt = reviewedAt,
    reviewedBy = reviewedBy,
    vacancy = practicumVacancies?.toDomain()
)

@Serializable
data class RegistrationInsertDto(
    @SerialName("user_id") val userId: String,
    @SerialName("vacancy_id") val vacancyId: Int,
    @SerialName("nilai_mk_syarat") val nilaiMkSyarat: String,
    @SerialName("transkrip_url") val transkripUrl: String,
    val status: String = "pending"
)
