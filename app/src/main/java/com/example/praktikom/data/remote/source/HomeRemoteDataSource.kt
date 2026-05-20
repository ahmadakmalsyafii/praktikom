package com.example.praktikom.data.remote.source

import com.example.praktikom.data.remote.dto.BannerDto
import com.example.praktikom.data.remote.dto.NotificationDto
import com.example.praktikom.data.remote.dto.PracticumClassDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

interface HomeRemoteDataSource {
    suspend fun getBanner(): List<BannerDto>
    suspend fun getAnnouncement(): List<NotificationDto>
    suspend fun getUserSchedule(): List<PracticumClassDto>
}

class HomeRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : HomeRemoteDataSource {

    override suspend fun getBanner(): List<BannerDto> {
        return supabaseClient.postgrest["banners"]
            .select()
            .decodeList<BannerDto>()
    }

    override suspend fun getAnnouncement(): List<NotificationDto> {
        return supabaseClient.postgrest["class_notifications"]
            .select()
            .decodeList<NotificationDto>()
    }

    override suspend fun getUserSchedule(): List<PracticumClassDto> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
            ?: throw Exception("User tidak login")

        return supabaseClient.postgrest["practicum_classes"]
            .select(Columns.raw( "*, class_enrollments!inner(*)")) {
                filter {
                    eq("class_enrollments.mahasiswa_id", userId)
                }
            }.decodeList<PracticumClassDto>()
    }
}
