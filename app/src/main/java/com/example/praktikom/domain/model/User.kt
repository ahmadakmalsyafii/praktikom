package com.example.praktikom.domain.model


data class User(
    val id: String,
    val nim: String,
    val nama: String,
    val email: String,
    val prodi: String,
    val role: String,
    val angkatan: String? = null,
    val fotoUrl: String? = null
)