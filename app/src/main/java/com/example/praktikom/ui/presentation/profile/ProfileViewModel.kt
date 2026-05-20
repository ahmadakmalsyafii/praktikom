package com.example.praktikom.ui.presentation.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.repository.AuthRepository
import com.example.praktikom.domain.usecase.GetProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfilUiState(
    val nama: String = "Memuat...",
    val nim: String = "-",
    val email: String = "-",
    val prodi: String = "-",
    val angkatan: String = "-",
    val role: String = "-",
    val fotoUrl: String? = null
)

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUserUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfilUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            getProfileUseCase().onSuccess { user ->
                _uiState.update {
                    it.copy(
                        nama = user.nama,
                        nim = user.nim,
                        email = user.email,
                        angkatan = user.angkatan?: "-",
                        fotoUrl = user.fotoUrl,
                        role = user.role?: "-",
                        prodi = user.prodi
                    )
                }
            }.onFailure {
                    _uiState.update {
                        it.copy(
                            nama = "Gagal memuat profil",
                            nim = "-",
                            email = "-",
                            angkatan = "-",
                            fotoUrl = null,
                            role = "-",
                            prodi = "-"
                        )
                    }
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
                .onSuccess {
                    onSuccess()
                }
        }
    }
}