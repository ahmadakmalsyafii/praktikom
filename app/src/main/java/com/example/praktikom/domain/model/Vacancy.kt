package com.example.praktikom.domain.model

data class Course(
    val id: Int,
    val sks: Int,
    val kodeMk: String,
    val namaMk: String
)

data class Vacancy(
    val id: Int,
    val courseId: Int,
    val tahunAjaran: String,
    val semester: String,
    val syaratNilaiMinimal: String,
    val syaratIpkMinimal: Double,
    val isActive: Boolean,
    val batasWaktuDaftar: String,
    val createdAt: String,
    val description: String?,
    val course: Course?
)
