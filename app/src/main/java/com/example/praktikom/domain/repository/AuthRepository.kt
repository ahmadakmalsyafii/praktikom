package com.example.praktikom.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    fun isUserLoggedIn(): Boolean
    suspend fun logout(): Result<Unit>
}