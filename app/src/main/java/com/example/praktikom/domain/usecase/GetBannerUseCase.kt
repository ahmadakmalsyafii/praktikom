package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Banner
import com.example.praktikom.domain.repository.HomeRepository
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Banner>> {
        return repository.getBanner()
    }
}