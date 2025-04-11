package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName
import java.util.Objects

open class DataResponse<T>(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("data")
    val data: T? = null,
)