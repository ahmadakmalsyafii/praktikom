package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email dan kata sandi tidak boleh kosong"))
        }
        return authRepository.login(email, password)
    }
}