package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.repository.VacancyRepository
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
    private val repository: VacancyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(VacancyDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val vacancyId: Int = checkNotNull(savedStateHandle["vacancyId"])

    init {
        loadVacancyDetail()
    }

    fun loadVacancyDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getVacancyDetail(vacancyId)
                .onSuccess { data ->
                    _uiState.update { it.copy(vacancy = data, isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(error = exception.message ?: "Terjadi kesalahan", isLoading = false) }
                }
        }
    }
}
