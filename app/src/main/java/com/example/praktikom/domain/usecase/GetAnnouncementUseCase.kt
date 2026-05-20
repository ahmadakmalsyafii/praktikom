package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.repository.AnnouncementRepository
import javax.inject.Inject

class GetAnnouncementUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(): Result<List<Announcement>> {
        return repository.getAnnouncement()
    }
}