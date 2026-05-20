package com.example.praktikom.domain.model

data class Registration(
    val id: Int,
    val userId: String,
    val vacancyId: Int,
    val nilaiMkSyarat: String,
    val transkripUrl: String,
    val status: String,
    val catatanReviewer: String?,
    val createdAt: String?,
    val reviewedAt: String?,
    val reviewedBy: String?,
    val vacancy: Vacancy?
)
