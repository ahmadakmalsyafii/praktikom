package com.example.praktikom.data.local.source

import com.example.praktikom.data.local.dao.UserDao
import com.example.praktikom.data.local.entity.toDomain
import com.example.praktikom.data.local.entity.toEntity
import com.example.praktikom.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

interface UserLocalDataSource {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun saveUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()?.toDomain()
    }

    override suspend fun clearUser() {
        userDao.clearUser()
    }
}