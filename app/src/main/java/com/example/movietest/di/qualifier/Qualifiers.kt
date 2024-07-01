package com.example.movietest.di.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PicassoHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class APPHttpClient
