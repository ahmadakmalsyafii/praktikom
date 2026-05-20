package com.example.praktikom.data.remote.source

import com.example.praktikom.data.remote.dto.InventoryDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

interface InventoryRemoteDataSource {
    suspend fun getInventory(): List<InventoryDto>
}

class InventoryRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : InventoryRemoteDataSource {

    override suspend fun getInventory(): List<InventoryDto> {
        return supabaseClient.postgrest["inventories"]
            .select()
            .decodeList<InventoryDto>()
    }
}

