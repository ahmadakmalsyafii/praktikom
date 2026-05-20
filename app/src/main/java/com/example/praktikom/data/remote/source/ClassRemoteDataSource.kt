package com.example.praktikom.data.remote.source

import com.example.praktikom.data.remote.dto.ClassNotificationDto
import com.example.praktikom.data.remote.dto.PracticumClassDto
import com.example.praktikom.data.remote.dto.PresenceSessionDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class EnrollmentWithUserDto(
    val class_id: Int,
    val mahasiswa_id: String,
    val users: UserBriefDto? = null
)

@Serializable
data class UserBriefDto(
    val nim: String,
    val nama: String
)

@Serializable
data class PresenceDto(
    val session_id: Int,
    val mahasiswa_id: String,
    val status_kehadiran: String?
)

interface ClassRemoteDataSource {
    suspend fun getClassesByAssistant(assistantId: String): List<PracticumClassDto>
    suspend fun getClassEnrollments(classId: Int): List<EnrollmentWithUserDto>
    suspend fun getPresenceSessions(classId: Int): List<PresenceSessionDto>
    suspend fun getPresencesForSessions(sessionIds: List<Int>): List<PresenceDto>
    suspend fun insertClassNotification(notification: ClassNotificationDto)
    suspend fun insertPresenceSession(session: PresenceSessionDto)
}

class ClassRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ClassRemoteDataSource {

    override suspend fun getClassesByAssistant(assistantId: String): List<PracticumClassDto> {
        return supabaseClient.postgrest["practicum_classes"]
            .select {
                filter {
                    eq("asisten_id", assistantId)
                }
            }
            .decodeList<PracticumClassDto>()
    }

    override suspend fun getClassEnrollments(classId: Int): List<EnrollmentWithUserDto> {
        return supabaseClient.postgrest["class_enrollments"]
            .select(columns = Columns.raw("class_id, mahasiswa_id, users(nim, nama)")) {
                filter {
                    eq("class_id", classId)
                }
            }
            .decodeList<EnrollmentWithUserDto>()
    }

    override suspend fun getPresenceSessions(classId: Int): List<PresenceSessionDto> {
        return supabaseClient.postgrest["presence_sessions"]
            .select {
                filter {
                    eq("class_id", classId)
                }
            }
            .decodeList<PresenceSessionDto>()
    }

    override suspend fun getPresencesForSessions(sessionIds: List<Int>): List<PresenceDto> {
        if (sessionIds.isEmpty()) return emptyList()
        return supabaseClient.postgrest["presences"]
            .select {
                filter {
                    isIn("session_id", sessionIds)
                }
            }
            .decodeList<PresenceDto>()
    }

    override suspend fun insertClassNotification(notification: ClassNotificationDto) {
        supabaseClient.postgrest["class_notifications"]
            .insert(notification)
    }

    override suspend fun insertPresenceSession(session: PresenceSessionDto) {
        supabaseClient.postgrest["presence_sessions"]
            .insert(session)
    }
}
