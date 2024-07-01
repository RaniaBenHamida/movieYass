package com.example.movietest.data.repository

import android.content.Context
import com.example.movietest.data.dataSource.apiclient.APIClient

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: APIClient
) : UserRepository {
    override suspend fun getUserData() {
        TODO("Not yet implemented")
    }

}
