package com.example.movietest.di.module

import com.example.movietest.data.useCase.GetDataRequestUseCase
import com.example.movietest.data.useCase.GetDataRequestUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetUserUseCase(useCase: GetDataRequestUseCaseImpl): GetDataRequestUseCase
}
