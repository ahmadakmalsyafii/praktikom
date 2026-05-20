package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.repository.ClassRepository
import javax.inject.Inject

class SendAnnouncementUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(classId: Int, judul: String, pesan: String): Result<Unit> {
        return classRepository.sendAnnouncement(classId, judul, pesan)
    }
}
