package com.example.movietest.di.module

import com.example.movietest.data.repository.UserRepository
import com.example.movietest.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
