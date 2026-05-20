package com.example.praktikom.ui.presentation.kelas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.usecase.GetJadwalAsistenUseCase
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

data class KelasState(
    val isLoading: Boolean = false,
    val classes: List<Schedule> = emptyList(),
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class KelasViewModel @Inject constructor(
    private val getJadwalAsistenUseCase: GetJadwalAsistenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(KelasState())
    val state: StateFlow<KelasState> = _state.asStateFlow()

    init {
        fetchClasses()
    }

    fun fetchClasses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getJadwalAsistenUseCase().fold(
                onSuccess = { classes ->
                    _state.update { it.copy(isLoading = false, classes = classes) }
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

    val filteredClasses = combine(_state) { stateArray ->
        val state = stateArray[0]
        state.classes.filter { schedule ->
            schedule.subject.contains(state.searchQuery, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
