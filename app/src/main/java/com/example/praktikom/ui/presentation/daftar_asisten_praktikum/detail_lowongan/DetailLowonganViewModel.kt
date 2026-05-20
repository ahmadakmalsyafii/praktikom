package com.example.praktikom.ui.presentation.daftar_asisten_praktikum.detail_lowongan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.usecase.GetVacancyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VacancyDetailUiState(
    val vacancy: Vacancy? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DetailLowonganViewModel @Inject constructor(
    private val getVacancyDetailUseCase: GetVacancyDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(VacancyDetailUiState())
    val uiState = _uiState.asStateFlow()

    // Mengambil vacancyId dari argumen navigasi secara aman
    private val vacancyId: Int = checkNotNull(savedStateHandle["vacancyId"])

    init {
        // Pemicu pertama kali saat ViewModel dibuat, UI cukup mengobservasi saja
        loadVacancyDetail()
    }

    fun loadVacancyDetail(id: Int = vacancyId) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getVacancyDetailUseCase(id)
                .onSuccess { data ->
                    _uiState.update { it.copy(vacancy = data, isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message ?: "Terjadi kesalahan",
                            isLoading = false
                        )
                    }
                }
        }
    }
}