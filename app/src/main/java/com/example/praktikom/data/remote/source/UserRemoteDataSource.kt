package com.example.praktikom.data.remote.source

import com.example.praktikom.data.remote.dto.UserDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import javax.inject.Inject

interface UserRemoteDataSource {
    suspend fun fetchProfileUser(): UserDto
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : UserRemoteDataSource {

    override suspend fun fetchProfileUser(): UserDto {
        val sessionUserId = supabaseClient.auth.currentUserOrNull()?.id
            ?: throw Exception("User tidak sedang login")

        return supabaseClient.postgrest["users"]
            .select(Columns.raw("*, study_programs(nama_prodi)")) {
                filter {
                    eq("id", sessionUserId)
                }
            }.decodeSingle<UserDto>()
    }
}
