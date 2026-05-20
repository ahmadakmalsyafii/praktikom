package com.example.praktikom.di

import com.example.praktikom.data.local.source.SessionLocalDataSource
import com.example.praktikom.data.local.source.SessionLocalDataSourceImpl
import com.example.praktikom.data.local.source.UserLocalDataSource
import com.example.praktikom.data.local.source.UserLocalDataSourceImpl
import com.example.praktikom.data.remote.source.HomeRemoteDataSource
import com.example.praktikom.data.remote.source.HomeRemoteDataSourceImpl
import com.example.praktikom.data.remote.source.UserRemoteDataSource
import com.example.praktikom.data.remote.source.UserRemoteDataSourceImpl
import com.example.praktikom.data.remote.source.VacancyRemoteDataSource
import com.example.praktikom.data.remote.source.VacancyRemoteDataSourceImpl
import com.example.praktikom.data.repository.AuthRepositoryImpl
import com.example.praktikom.data.repository.HomeRepositoryImpl
import com.example.praktikom.data.repository.UserRepositoryImpl
import com.example.praktikom.data.repository.InventoryRepositoryImpl
import com.example.praktikom.data.repository.ClassRepositoryImpl
import com.example.praktikom.data.remote.source.InventoryRemoteDataSource
import com.example.praktikom.data.remote.source.InventoryRemoteDataSourceImpl
import com.example.praktikom.data.remote.source.ClassRemoteDataSource
import com.example.praktikom.data.remote.source.ClassRemoteDataSourceImpl
import com.example.praktikom.data.repository.VacancyRepositoryImpl
import com.example.praktikom.domain.repository.AuthRepository
import com.example.praktikom.domain.repository.HomeRepository
import com.example.praktikom.domain.repository.InventoryRepository
import com.example.praktikom.domain.repository.ClassRepository
import com.example.praktikom.domain.repository.UserRepository
import com.example.praktikom.domain.repository.VacancyRepository
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
    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository
    @Binds
    abstract fun bindVacancyRepository(
        vacancyRepositoryImpl: VacancyRepositoryImpl
    ): VacancyRepository

    @Binds
    abstract fun bindInventoryRepository(
        inventoryRepositoryImpl: InventoryRepositoryImpl
    ): InventoryRepository

    @Binds
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository

    // Data Source
    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindHomeRemoteDataSource(
        homeRemoteDataSource: HomeRemoteDataSourceImpl
    ): HomeRemoteDataSource

    @Binds
    abstract fun bindVacancyRemoteDataSource(
        vacancyRemoteDataSourceImpl: VacancyRemoteDataSourceImpl
    ): VacancyRemoteDataSource

    @Binds
    abstract fun bindInventoryRemoteDataSource(
        inventoryRemoteDataSourceImpl: InventoryRemoteDataSourceImpl
    ): InventoryRemoteDataSource

    @Binds
    abstract fun bindClassRemoteDataSource(
        classRemoteDataSourceImpl: ClassRemoteDataSourceImpl
    ): ClassRemoteDataSource

    @Binds
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindSessionLocalDataSource(
        sessionLocalDataSourceImpl: SessionLocalDataSourceImpl
    ): SessionLocalDataSource


}
