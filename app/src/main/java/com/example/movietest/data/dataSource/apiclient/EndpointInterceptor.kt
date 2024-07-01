package com.example.movietest.data.dataSource.apiclient

import android.content.Context
import com.example.movietest.global.extension.isNetworkAvailable
import com.example.movietest.utils.NoInternetException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TOKEN_KEY = "Authorization"
private const val ACCEPT_KEY = "Accept:"
private const val APPLICATION_JSON_KEY = "application/json"


@Singleton
class EndpointInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (context.isNetworkAvailable()) {
            val requestBuilder = request.newBuilder()

            requestBuilder
                    .addHeader(TOKEN_KEY, "Client-ID J_kUoSQ8cTEY7nffb5HodbiCo5uKdKwiTRNBuVUghxo")
                    .build()


            request = requestBuilder.build()
        } else {
            throw NoInternetException("No network available")
        }

        return chain.proceed(request)

    }
}