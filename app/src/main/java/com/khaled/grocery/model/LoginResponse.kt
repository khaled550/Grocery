package com.khaled.grocery.model

import User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: User
)

data class LoginRequest(val email: String, val password: String)
