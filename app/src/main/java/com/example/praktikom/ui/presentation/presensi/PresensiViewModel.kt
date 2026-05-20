package com.example.praktikom.ui.presentation.presensi


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.praktikom.domain.model.PresenceSession
import com.example.praktikom.domain.usecase.CreatePresenceSessionUseCase
import com.example.praktikom.domain.usecase.GetPresenceSessionsUseCase
import com.example.praktikom.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class PresensiState(
    val classId: Int = 0,
    val topic: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val sessions: List<PresenceSession> = emptyList(),
    val ongoingSessions: List<PresenceSession> = emptyList(),
    val historySessions: List<PresenceSession> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val isCreating: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PresensiViewModel @Inject constructor(
    private val getPresenceSessionsUseCase: GetPresenceSessionsUseCase,
    private val createPresenceSessionUseCase: CreatePresenceSessionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PresensiState())
    val state: StateFlow<PresensiState> = _state.asStateFlow()

    init {
        val route = savedStateHandle.toRoute<Route.PresensiScreen>()
        val classId = route.classId
        _state.update { it.copy(classId = classId) }

        if (classId != 0) {
            fetchSessions(classId)
        }
    }

    private fun fetchSessions(classId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getPresenceSessionsUseCase(classId).fold(
                onSuccess = { sessions ->
                    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val today = df.format(Date())

                    val ongoing = sessions.filter { it.date >= today }
                    val history = sessions.filter { it.date < today }

                    _state.update { it.copy(
                        isLoading = false,
                        sessions = sessions,
                        ongoingSessions = ongoing,
                        historySessions = history
                    ) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun onTopicChange(topic: String) { _state.update { it.copy(topic = topic) } }
    fun onDateChange(date: String) { _state.update { it.copy(date = date) } }
    fun onStartTimeChange(time: String) { _state.update { it.copy(startTime = time) } }
    fun onEndTimeChange(time: String) { _state.update { it.copy(endTime = time) } }
    fun onTabSelected(index: Int) { _state.update { it.copy(selectedTab = index) } }

    fun createPresence() {
        val s = _state.value
        if (s.topic.isBlank() || s.date.isBlank() || s.startTime.isBlank() || s.endTime.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isCreating = true) }

            val pertemuanKe = s.sessions.size + 1

            createPresenceSessionUseCase(
                classId = s.classId,
                pertemuanKe = pertemuanKe,
                tanggal = s.date,
                jamBuka = s.startTime,
                jamTutup = s.endTime
            ).fold(
                onSuccess = {
                    _state.update { it.copy(
                        isCreating = false,
                        topic = "",
                        date = "",
                        startTime = "",
                        endTime = ""
                    ) }
                    fetchSessions(s.classId)
                },
                onFailure = { error ->
                    _state.update { it.copy(isCreating = false, error = error.message) }
                }
            )
        }
    }
}
