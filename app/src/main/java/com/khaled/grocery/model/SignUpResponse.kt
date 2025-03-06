package com.khaled.grocery.model

import User
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: User
)

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String?, val message: String?)