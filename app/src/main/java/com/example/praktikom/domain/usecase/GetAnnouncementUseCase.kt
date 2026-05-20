package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.repository.HomeRepository
import javax.inject.Inject

class GetAnnouncementUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Announcement>> {
        return repository.getAnnouncement()
    }
}