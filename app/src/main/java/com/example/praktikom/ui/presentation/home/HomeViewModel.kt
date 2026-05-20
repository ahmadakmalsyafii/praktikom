package com.example.praktikom.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.model.Banner
import com.example.praktikom.domain.model.Schedule
import com.example.praktikom.domain.usecase.GetAnnouncementUseCase
import com.example.praktikom.domain.usecase.GetBannerUseCase
import com.example.praktikom.domain.usecase.GetJadwalAsistenUseCase
import com.example.praktikom.domain.usecase.GetProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "Memuat...",
    val banners: List<Banner> = emptyList(),
    val schedules: List<Schedule> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUserUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getAnnouncementUseCase: GetAnnouncementUseCase,
    private val getJadwalAsistenUseCase: GetJadwalAsistenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val user = getProfileUseCase().getOrNull()
            val banners = getBannerUseCase().getOrDefault(emptyList())
            val schedules = getJadwalAsistenUseCase().getOrDefault(emptyList())
            val announcements = getAnnouncementUseCase().getOrDefault(emptyList())

            _uiState.update {
                it.copy(
                    userName = user?.nama ?: "Pengguna",
                    banners = banners,
                    schedules = schedules,
                    announcements = announcements,
                    isLoading = false
                )
            }
        }
    }
}