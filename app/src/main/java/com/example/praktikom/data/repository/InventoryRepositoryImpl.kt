package com.example.praktikom.data.repository

import com.example.praktikom.data.remote.dto.toDomain
import com.example.praktikom.data.remote.source.InventoryRemoteDataSource
import com.example.praktikom.domain.model.Inventory
import com.example.praktikom.domain.repository.InventoryRepository
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: InventoryRemoteDataSource
) : InventoryRepository {

    override suspend fun getInventory(): Result<List<Inventory>> = try {
        Result.success(remoteDataSource.getInventory().map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }
}
