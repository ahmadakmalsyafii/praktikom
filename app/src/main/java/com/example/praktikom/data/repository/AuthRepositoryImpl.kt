package com.example.praktikom.data.repository

import com.example.praktikom.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import com.example.praktikom.data.local.SessionManager
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            sessionManager.saveLoginState(true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    suspend fun logout(): Result<Unit> {
        return try {
            supabaseClient.auth.signOut()
            sessionManager.clearSession()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}