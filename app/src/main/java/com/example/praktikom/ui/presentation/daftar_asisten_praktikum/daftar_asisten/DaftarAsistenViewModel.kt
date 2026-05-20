package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.usecase.GetVacanciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VacanciesUiState(
    val vacancies: List<Vacancy> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DaftarAsistenViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VacanciesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadVacancies()
    }

    fun loadVacancies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getVacanciesUseCase()
                .onSuccess { list ->
                    _uiState.update { it.copy(vacancies = list, isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(error = exception.message ?: "Terjadi kesalahan", isLoading = false) }
                }
        }
    }
}
