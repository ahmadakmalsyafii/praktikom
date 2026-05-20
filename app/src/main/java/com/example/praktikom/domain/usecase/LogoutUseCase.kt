package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.repository.AuthRepository

import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}