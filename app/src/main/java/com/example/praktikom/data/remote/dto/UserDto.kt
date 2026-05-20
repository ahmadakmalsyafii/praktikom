package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StudyProgramDto(
    @SerialName("nama_prodi") val namaProdi: String
)


@Serializable
data class UserDto(
    val id: String,
    val nim: String,
    val nama: String,
    val email: String,
    val role: String,
    val angkatan: Int? = null,
    @SerialName("foto_url") val fotoUrl: String? = null,
    @SerialName("study_programs") val studyProgram: StudyProgramDto? = null
)

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        nim = this.nim,
        nama = this.nama,
        email = this.email,
        role = this.role,
        angkatan = this.angkatan?.toString() ?: "-",
        prodi = this.studyProgram?.namaProdi ?: "-",
        fotoUrl = this.fotoUrl
    )
}