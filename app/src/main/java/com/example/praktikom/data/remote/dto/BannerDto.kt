package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.Banner
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val id: Int,
    val title: String,
    val description: String,
    @SerialName("image_url") val imageUrl: String
)

fun BannerDto.toDomain() = Banner(id, title, description, imageUrl)