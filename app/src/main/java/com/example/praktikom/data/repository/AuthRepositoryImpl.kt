package com.example.praktikom.data.repository

import com.example.praktikom.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import com.example.praktikom.data.local.source.SessionLocalDataSource
import com.example.praktikom.data.local.source.UserLocalDataSource
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val sessionLocalDataSource: SessionLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            sessionLocalDataSource.saveLoginState(true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return sessionLocalDataSource.isLoggedIn()
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            supabaseClient.auth.signOut()
            sessionLocalDataSource.clearSession()
            userLocalDataSource.clearUser()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}