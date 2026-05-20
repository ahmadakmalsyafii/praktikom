package com.example.praktikom.ui.presentation.class_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.praktikom.domain.model.StudentEnrollment
import com.example.praktikom.domain.usecase.GetStudentEnrollmentsUseCase
import com.example.praktikom.domain.usecase.SendAnnouncementUseCase
import com.example.praktikom.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class KelasDetailState(
    val classId: Int = 0,
    val subject: String = "",
    val timeInfo: String = "",
    val room: String = "",
    val students: List<StudentEnrollment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAnnouncementDialog: Boolean = false,
    val announcementTitle: String = "",
    val announcementDesc: String = "",
    val isSendingAnnouncement: Boolean = false
)

@HiltViewModel
class KelasDetailViewModel @Inject constructor(
    private val getStudentEnrollmentsUseCase: GetStudentEnrollmentsUseCase,
    private val sendAnnouncementUseCase: SendAnnouncementUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(KelasDetailState())
    val state: StateFlow<KelasDetailState> = _state.asStateFlow()

    init {
        val route = savedStateHandle.toRoute<Route.KelasDetailScreen>()
        val classId = route.classId
        val subject  = route.subject
        val timeInfo = route.timeInfo
        val room     = route.room

        _state.update { it.copy(
            classId = classId,
            subject = subject,
            timeInfo = timeInfo,
            room = room
        ) }

        if (classId != 0) {
            fetchStudents(classId)
        }
    }

    private fun fetchStudents(classId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getStudentEnrollmentsUseCase(classId).fold(
                onSuccess = { students ->
                    _state.update { it.copy(isLoading = false, students = students) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun onAnnouncementDialogToggle(show: Boolean) {
        _state.update { it.copy(showAnnouncementDialog = show) }
    }

    fun onAnnouncementDescChange(desc: String) {
        _state.update { it.copy(announcementDesc = desc) }
    }

    fun onAnnouncementTitleChange(title: String) {
        _state.update { it.copy(announcementTitle = title) }
    }

    fun sendAnnouncement() {
        val currentDesc = _state.value.announcementDesc
        val currentTitle = _state.value.announcementTitle
        if (currentDesc.isBlank() || currentTitle.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isSendingAnnouncement = true) }
            sendAnnouncementUseCase(
                classId = _state.value.classId,
                judul = currentTitle,
                pesan = currentDesc
            ).fold(
                onSuccess = {
                    _state.update { it.copy(
                        isSendingAnnouncement = false,
                        showAnnouncementDialog = false,
                        announcementDesc = "",
                        announcementTitle = ""
                    ) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isSendingAnnouncement = false, error = error.message) }
                }
            )
        }
    }
}
