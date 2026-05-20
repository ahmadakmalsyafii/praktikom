package com.example.praktikom.ui.presentation.pinjam_barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Inventory
import com.example.praktikom.domain.usecase.GetInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinjamBarangState(
    val isLoading: Boolean = false,
    val inventories: List<Inventory> = emptyList(),
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class PinjamBarangViewModel @Inject constructor(
    private val getInventoryUseCase: GetInventoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PinjamBarangState())
    val state: StateFlow<PinjamBarangState> = _state.asStateFlow()

    init {
        fetchInventories()
    }

    private fun fetchInventories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getInventoryUseCase().fold(
                onSuccess = { inventories ->
                    _state.update { it.copy(isLoading = false, inventories = inventories) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    val filteredInventories = combine(_state) { stateArray ->
        val state = stateArray[0]
        state.inventories.filter { inventory ->
            inventory.namaAlat.contains(state.searchQuery, ignoreCase = true) ||
            inventory.kodeAlat.contains(state.searchQuery, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}