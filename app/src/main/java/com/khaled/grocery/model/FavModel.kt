package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class FavModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("product")
    val product: Product? = null,
)
