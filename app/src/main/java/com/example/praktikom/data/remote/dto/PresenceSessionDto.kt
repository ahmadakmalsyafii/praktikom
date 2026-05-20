package com.example.praktikom.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresenceSessionDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("class_id") val classId: Int,
    @SerialName("pertemuan_ke") val pertemuanKe: Int,
    @SerialName("tanggal") val tanggal: String,
    @SerialName("jam_buka") val jamBuka: String,
    @SerialName("jam_tutup") val jamTutup: String,
    @SerialName("created_by") val createdBy: String? = null
)
