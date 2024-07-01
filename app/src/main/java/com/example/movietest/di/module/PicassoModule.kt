package com.example.movietest.di.module

import android.content.Context
import com.example.movietest.di.qualifier.PicassoHttpClient
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PicassoModule {


    @Provides
    @Singleton
    fun providesPicasso(
        @ApplicationContext context: Context,
        okHttp3Downloader: OkHttp3Downloader
    ) =
        Picasso
            .Builder(context)
            .loggingEnabled(true)
            .downloader(okHttp3Downloader)
            .build()

    @Provides
    @Singleton
    fun providesOkHttp3Downloader(@PicassoHttpClient okHttpClient: OkHttpClient) =
        OkHttp3Downloader(okHttpClient)
}