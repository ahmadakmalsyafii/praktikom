package com.example.praktikom.ui.presentation.jadwal_praktikum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.usecase.GetJadwalAsistenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JadwalPraktikumState(
    val schedules: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class JadwalPraktikumViewModel @Inject constructor(
    private val getJadwalAsistenUseCase: GetJadwalAsistenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JadwalPraktikumState())
    val state: StateFlow<JadwalPraktikumState> = _state.asStateFlow()

    init {
        loadSchedules()
    }

    fun loadSchedules() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getJadwalAsistenUseCase().fold(
                onSuccess = { schedules ->
                    _state.update { it.copy(schedules = schedules, isLoading = false) }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Terjadi kesalahan") }
                }
            )
        }
    }
}
