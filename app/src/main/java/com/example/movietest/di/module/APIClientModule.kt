package com.example.movietest.di.module

import com.example.movietest.BuildConfig
import com.example.movietest.data.dataSource.apiclient.APIClient
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class APIClientModule {

    @Provides
    @Singleton
    fun apiClient(retrofit: Retrofit): APIClient {
        return retrofit.create(APIClient::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
}