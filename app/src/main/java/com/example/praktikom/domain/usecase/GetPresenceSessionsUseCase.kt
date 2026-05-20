package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.PresenceSession
import com.example.praktikom.domain.repository.ClassRepository
import javax.inject.Inject

class GetPresenceSessionsUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(classId: Int): Result<List<PresenceSession>> {
        return classRepository.getPresenceSessions(classId)
    }
}
