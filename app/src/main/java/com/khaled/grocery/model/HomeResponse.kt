package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: Any? = null,
    @SerializedName("status")
    val status: Boolean? = null
)