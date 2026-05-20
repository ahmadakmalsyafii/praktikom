package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikom.domain.model.User
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.domain.usecase.ApplyVacancyUseCase
import com.example.praktikom.domain.usecase.GetVacancyDetailUseCase
import com.example.praktikom.domain.usecase.GetProfileUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormPendaftaranUiState(
    val user: User? = null,
    val vacancy: Vacancy? = null,
    val grade: String = "",
    val fileName: String? = null,
    val fileBytes: ByteArray? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val submitSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FormPendaftaranViewModel @Inject constructor(
    private val getVacancyDetailUseCase: GetVacancyDetailUseCase,
    private val applyVacancyUseCase: ApplyVacancyUseCase,
    private val getProfileUseCase: GetProfileUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormPendaftaranUiState())
    val uiState = _uiState.asStateFlow()

    private val vacancyId: Int = checkNotNull(savedStateHandle["vacancyId"])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val userResult = getProfileUseCase()
            val vacancyResult = getVacancyDetailUseCase(vacancyId)

            if (vacancyResult.isSuccess && userResult.isSuccess) {
                _uiState.update {
                    it.copy(
                        user = userResult.getOrNull(),
                        vacancy = vacancyResult.getOrNull(),
                        isLoading = false
                    )
                }
            } else {
                val errMsg = vacancyResult.exceptionOrNull()?.message 
                    ?: userResult.exceptionOrNull()?.message 
                    ?: "Gagal memuat data"
                _uiState.update { it.copy(error = errMsg, isLoading = false) }
            }
        }
    }

    fun onGradeChange(grade: String) {
        _uiState.update { it.copy(grade = grade) }
    }

    fun onFileSelected(name: String, bytes: ByteArray) {
        _uiState.update { it.copy(fileName = name, fileBytes = bytes) }
    }

    fun submitForm() {
        val state = _uiState.value
        val bytes = state.fileBytes
        val name = state.fileName
        val user = state.user
        val vacancy = state.vacancy

        if (user == null || vacancy == null) {
            _uiState.update { it.copy(error = "Data belum lengkap") }
            return
        }

        if (state.grade.isBlank()) {
            _uiState.update { it.copy(error = "Predikat nilai harus diisi") }
            return
        }

        if (bytes == null || name == null) {
            _uiState.update { it.copy(error = "KHS belum diunggah") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }
            
            // Format: transkrip/nim_vacancyId_timestamp.ext
            val extension = name.substringAfterLast('.', "pdf")
            val formattedName = "${user.nim}_${vacancyId}_${System.currentTimeMillis()}.$extension"

            applyVacancyUseCase(vacancyId, state.grade, bytes, formattedName)
                .onSuccess {
                    _uiState.update { it.copy(isSubmitting = false, submitSuccess = true) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(isSubmitting = false, error = exception.message ?: "Gagal mendaftar") }
                }
        }
    }
}
