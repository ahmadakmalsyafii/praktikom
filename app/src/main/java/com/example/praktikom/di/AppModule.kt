package com.example.praktikom.di

import com.example.praktikom.data.local.source.SessionLocalDataSource
import com.example.praktikom.data.local.source.SessionLocalDataSourceImpl
import com.example.praktikom.data.local.source.UserLocalDataSource
import com.example.praktikom.data.local.source.UserLocalDataSourceImpl
import com.example.praktikom.data.remote.source.UserRemoteDataSource
import com.example.praktikom.data.remote.source.UserRemoteDataSourceImpl
import com.example.praktikom.data.repository.AuthRepositoryImpl
import com.example.praktikom.data.repository.UserRepositoryImpl
import com.example.praktikom.domain.repository.AuthRepository
import com.example.praktikom.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // Repository
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    // Data Source
    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindSessionLocalDataSource(
        sessionLocalDataSourceImpl: SessionLocalDataSourceImpl
    ): SessionLocalDataSource


}