package com.example.movietest.di.module

import android.content.Context
import com.example.movietest.utils.CONNECT_TIMEOUT
import com.example.movietest.utils.OKHTTP_CACHE_FILE_NAME
import com.example.movietest.utils.READ_TIMEOUT
import com.example.movietest.utils.WRITE_TIMEOUT
import com.example.movietest.data.dataSource.apiclient.EndpointInterceptor
import com.example.movietest.di.qualifier.PicassoHttpClient
import com.example.movietest.helpers.DebugLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                DebugLog.d("intercept-movie", message)
            }

        }).run {
            level = HttpLoggingInterceptor.Level.BODY
            this
        }

    @Provides
    @Singleton
    fun providesOkHTTPClient(
        loggingInterceptor: HttpLoggingInterceptor,
        endpointInterceptor: EndpointInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(endpointInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesCacheFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, OKHTTP_CACHE_FILE_NAME)


    @Provides
    @Singleton
    fun providesCache(cacheFile: File): Cache = Cache(cacheFile, 10L * 1000 * 1000)//10MB Cache


    @Provides
    @Singleton
    @PicassoHttpClient
    fun providesPicassoOkHTTPClient(
        interceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .cache(cache)
            .build()


}