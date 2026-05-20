package com.example.praktikom.data.repository

import com.example.praktikom.data.local.source.UserLocalDataSource
import com.example.praktikom.data.remote.dto.ClassNotificationDto
import com.example.praktikom.data.remote.dto.PresenceSessionDto
import com.example.praktikom.data.remote.dto.toDomain
import com.example.praktikom.data.remote.source.ClassRemoteDataSource
import com.example.praktikom.domain.model.PresenceSession
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.model.StudentEnrollment
import com.example.praktikom.domain.repository.ClassRepository

import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClassRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : ClassRepository {

    override suspend fun getClassesByAssistant(): Result<List<Schedule>> = try {
        val userId = userLocalDataSource.getUser()?.id ?: throw Exception("User not logged in")
        val classes = remoteDataSource.getClassesByAssistant(userId)
        Result.success(classes.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getStudentEnrollmentsWithPresence(classId: Int): Result<List<StudentEnrollment>> = try {
        val enrollments = remoteDataSource.getClassEnrollments(classId)
        val sessions = remoteDataSource.getPresenceSessions(classId)
        
        val totalSessions = sessions.size
        
        val presences = if (totalSessions > 0) {
            remoteDataSource.getPresencesForSessions(sessions.map { it.id })
        } else {
            emptyList()
        }

        val result = enrollments.mapNotNull { enrollment ->
            val user = enrollment.users ?: return@mapNotNull null

            val studentPresences = presences.filter { it.mahasiswa_id == enrollment.mahasiswa_id }
            val attendedSessions = studentPresences.count { 
                it.status_kehadiran == "hadir_luring" || it.status_kehadiran == "hadir_daring" || it.status_kehadiran == "izin"
            }
            
            val percentage = if (totalSessions > 0) {
                (attendedSessions.toFloat() / totalSessions.toFloat() * 100).toInt()
            } else {
                100
            }

            StudentEnrollment(
                nim = user.nim,
                name = user.nama,
                presencePercentage = percentage
            )
        }
        
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun sendAnnouncement(classId: Int, judul: String, pesan: String): Result<Unit> = try {
        val userId = userLocalDataSource.getUser()?.id ?: throw Exception("User not logged in")
        val notification = ClassNotificationDto(
            classId = classId,
            asistenId = userId,
            judul = judul,
            pesan = pesan
        )
        remoteDataSource.insertClassNotification(notification)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getPresenceSessions(classId: Int): Result<List<PresenceSession>> = try {
        val sessions = remoteDataSource.getPresenceSessions(classId)
        val result = sessions.map {
            val startTimeStr = if (it.jamBuka.contains("T")) it.jamBuka.substringAfter("T").take(5) else if (it.jamBuka.length >= 5) it.jamBuka.take(5) else it.jamBuka
            val endTimeStr = if (it.jamTutup.contains("T")) it.jamTutup.substringAfter("T").take(5) else if (it.jamTutup.length >= 5) it.jamTutup.take(5) else it.jamTutup
            
            PresenceSession(
                id = it.id,
                classId = it.classId,
                topic = "Pertemuan ${it.pertemuanKe}", 
                date = it.tanggal,
                startTime = startTimeStr,
                endTime = endTimeStr
            )
        }
        Result.success(result.sortedByDescending { it.id })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun createPresenceSession(
        classId: Int,
        pertemuanKe: Int,
        tanggal: String,
        jamBuka: String,
        jamTutup: String
    ): Result<Unit> = try {
        val userId = userLocalDataSource.getUser()?.id ?: throw Exception("User not logged in")

        // Format into ISO 8601 timestamp with +07:00 timezone (WIB)
        val jamBukaTime = "${tanggal}T${jamBuka}:00+07:00"
        val jamTutupTime = "${tanggal}T${jamTutup}:00+07:00"
        
        val session = PresenceSessionDto(
            classId = classId,
            pertemuanKe = pertemuanKe,
            tanggal = tanggal,
            jamBuka = jamBukaTime,
            jamTutup = jamTutupTime,
            createdBy = userId
        )
        remoteDataSource.insertPresenceSession(session)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
