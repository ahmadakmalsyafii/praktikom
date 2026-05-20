package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.Course
import com.example.praktikom.domain.model.Vacancy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    val id: Int,
    val sks: Int,
    @SerialName("kode_mk") val kodeMk: String,
    @SerialName("nama_mk") val namaMk: String
)

fun CourseDto.toDomain() = Course(
    id = id,
    sks = sks,
    kodeMk = kodeMk,
    namaMk = namaMk
)

@Serializable
data class VacancyDto(
    val id: Int,
    @SerialName("course_id") val courseId: Int,
    @SerialName("tahun_ajaran") val tahunAjaran: String,
    val semester: String,
    @SerialName("syarat_nilai_minimal") val syaratNilaiMinimal: String,
    @SerialName("syarat_ipk_minimal") val syaratIpkMinimal: Double,
    @SerialName("is_active") val isActive: Boolean,
    @SerialName("batas_waktu_daftar") val batasWaktuDaftar: String,
    @SerialName("created_at") val createdAt: String,
    val description: String? = null,
    val courses: CourseDto? = null
)

fun VacancyDto.toDomain() = Vacancy(
    id = id,
    courseId = courseId,
    tahunAjaran = tahunAjaran,
    semester = semester,
    syaratNilaiMinimal = syaratNilaiMinimal,
    syaratIpkMinimal = syaratIpkMinimal,
    isActive = isActive,
    batasWaktuDaftar = batasWaktuDaftar,
    createdAt = createdAt,
    description = description,
    course = courses?.toDomain()
)
