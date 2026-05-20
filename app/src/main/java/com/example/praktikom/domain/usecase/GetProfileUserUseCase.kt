package com.example.praktikom.domain.usecase

import com.example.praktikom.domain.model.User
import com.example.praktikom.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getProfileUser()
    }
}