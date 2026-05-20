package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Registration
import com.example.praktikom.domain.repository.VacancyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RiwayatPendaftaranUiState(
    val registrations: List<Registration> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RiwayatPendaftaranViewModel @Inject constructor(
    private val repository: VacancyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RiwayatPendaftaranUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRegistrations()
    }

    fun loadRegistrations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getRegistrations()
                .onSuccess { list ->
                    _uiState.update { it.copy(registrations = list, isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(error = exception.message ?: "Terjadi kesalahan", isLoading = false) }
                }
        }
    }
}
