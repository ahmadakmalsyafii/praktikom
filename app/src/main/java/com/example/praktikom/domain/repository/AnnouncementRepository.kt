package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Announcement

interface AnnouncementRepository {
    suspend fun getAnnouncement(): Result<List<Announcement>>
}