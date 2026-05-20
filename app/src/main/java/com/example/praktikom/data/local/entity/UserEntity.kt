package com.example.praktikom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.praktikom.domain.model.User

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: String,
    val nim: String,
    val nama: String,
    val email: String,
    val prodi: String,
    val role: String,
    val angkatan: String?,
    val fotoUrl: String?
)


fun UserEntity.toDomain(): User {
    return User(id, nim, nama, email, prodi, role, angkatan, fotoUrl)
}

fun User.toEntity(): UserEntity {
    return UserEntity(id, nim, nama, email, prodi, role, angkatan, fotoUrl)
}