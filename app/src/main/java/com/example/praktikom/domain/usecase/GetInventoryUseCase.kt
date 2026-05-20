package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.Inventory
import com.example.praktikom.domain.repository.InventoryRepository
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    suspend operator fun invoke(): Result<List<Inventory>> {
        return repository.getInventory()
    }
}