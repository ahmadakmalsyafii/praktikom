package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.Schedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticumClassDto(
    val id: Int,
    @SerialName("nama_kelas") val namaKelas: String,
    val hari: String,
    @SerialName("jam_mulai") val jamMulai: String,
    @SerialName("jam_selesai") val jamSelesai: String,
    val ruang: String
)


fun PracticumClassDto.toDomain() = Schedule(
    id = this.id.toString(),
    subject = this.namaKelas,
    room = this.ruang,
    day = this.hari,
    time = "${this.jamMulai.take(5)} - ${this.jamSelesai.take(5)}"
)