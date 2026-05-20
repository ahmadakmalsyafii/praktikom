package com.example.praktikom.data.repository

import com.example.praktikom.data.local.source.UserLocalDataSource
import com.example.praktikom.data.remote.source.UserRemoteDataSource
import com.example.praktikom.data.remote.dto.UserDto
import com.example.praktikom.data.remote.dto.toDomain
import com.example.praktikom.domain.model.User
import com.example.praktikom.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getProfileUser(): Result<User> {
        return try {
            val userDto = remoteDataSource.fetchProfileUser()
            val domainUser = userDto.toDomain()

            localDataSource.saveUser(domainUser)

            Result.success(domainUser)
        } catch (e: Exception) {
            val localUser = localDataSource.getUser()

            if (localUser != null) {
                Result.success(localUser)
            } else {
                Result.failure(e)
            }
        }
    }
}