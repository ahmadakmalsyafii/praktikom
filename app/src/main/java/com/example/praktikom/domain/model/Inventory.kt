package com.example.praktikom.domain.model

data class Inventory(
    val id: Int,
    val kodeAlat: String,
    val namaAlat: String,
    val jumlahTotal: Int,
    val jumlahTersedia: Int,
    val kondisi: String,
    val fotoUrl: String?
)