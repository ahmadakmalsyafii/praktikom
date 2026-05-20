package com.example.praktikom.domain.repository

import com.example.praktikom.domain.model.Inventory

interface InventoryRepository {
    suspend fun getInventory(): Result<List<Inventory>>
}