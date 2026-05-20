package com.example.praktikom.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassNotificationDto(
    @SerialName("class_id") val classId: Int,
    @SerialName("asisten_id") val asistenId: String? = null,
    @SerialName("judul") val judul: String,
    @SerialName("pesan") val pesan: String
)
