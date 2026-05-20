package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.model.Banner
import com.example.praktikom.domain.model.Schedule

interface  HomeRepository {
    suspend fun getBanner(): Result<List<Banner>>
    suspend fun getAnnouncement(): Result<List<Announcement>>
    suspend fun getSchedule(): Result<List<Schedule>>
}