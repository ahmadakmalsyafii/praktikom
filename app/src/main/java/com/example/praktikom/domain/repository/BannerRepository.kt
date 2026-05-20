package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Banner

interface BannerRepository {
        suspend fun getBanner(): Result<List<Banner>>
}