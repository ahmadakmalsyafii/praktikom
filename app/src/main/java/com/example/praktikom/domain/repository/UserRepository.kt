package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.User

interface UserRepository {
    suspend fun getProfileUser(): Result<User>
}