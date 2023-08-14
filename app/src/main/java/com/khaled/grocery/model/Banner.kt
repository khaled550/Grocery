package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("category")
    val category: Any? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("product")
    val product: Any? = null
)