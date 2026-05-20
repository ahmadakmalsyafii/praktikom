package com.example.praktikom.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.praktikom.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user_profile")
    suspend fun clearUser()
}