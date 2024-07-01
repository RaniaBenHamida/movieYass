package com.example.movietest.data.model.token

import com.google.gson.annotations.SerializedName

data class Token(


    @SerializedName("access_token")
    val token: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expirationDate: Long
)