package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.PresenceSession
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.model.StudentEnrollment

interface ClassRepository {
    suspend fun getClassesByAssistant(): Result<List<Schedule>>
    suspend fun getStudentEnrollmentsWithPresence(classId: Int): Result<List<StudentEnrollment>>
    suspend fun sendAnnouncement(classId: Int, judul: String, pesan: String): Result<Unit>
    suspend fun getPresenceSessions(classId: Int): Result<List<PresenceSession>>
    suspend fun createPresenceSession(classId: Int, pertemuanKe: Int, tanggal: String, jamBuka: String, jamTutup: String): Result<Unit>
}
