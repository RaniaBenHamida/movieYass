package com.example.movietest.di.module

import com.example.movietest.utils.BooleanSerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class GsonModule {


    @Provides
    @Singleton
    fun provideBooleanSerializer() = BooleanSerializer()

    @Provides
    @Singleton
    fun providesGson(serializer: BooleanSerializer): Gson =
        GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, serializer)
            .setPrettyPrinting().setLenient().create()

}