package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.StudentEnrollment
import com.example.praktikom.domain.repository.ClassRepository
import javax.inject.Inject

class GetStudentEnrollmentsUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(classId: Int): Result<List<StudentEnrollment>> {
        return classRepository.getStudentEnrollmentsWithPresence(classId)
    }
}
