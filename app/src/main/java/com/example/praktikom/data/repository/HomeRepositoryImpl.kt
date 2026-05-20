package com.example.praktikom.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.praktikom.data.remote.dto.BannerDto
import com.example.praktikom.data.remote.dto.NotificationDto
import com.example.praktikom.data.remote.dto.toDomain
import com.example.praktikom.data.remote.source.HomeRemoteDataSource
import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.model.Banner
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.repository.HomeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject


class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getBanner(): Result<List<Banner>> = try {
        Result.success(remoteDataSource.getBanner().map { it.toDomain() })
    } catch (e: Exception) { Result.failure(e) }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAnnouncement(): Result<List<Announcement>> = try {
        Result.success(remoteDataSource.getAnnouncement().map { it.toDomain() })
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun getSchedule(): Result<List<Schedule>> = try {
        Result.success(remoteDataSource.getUserSchedule().map { it.toDomain() })
    } catch (e: Exception) { Result.failure(e) }


}