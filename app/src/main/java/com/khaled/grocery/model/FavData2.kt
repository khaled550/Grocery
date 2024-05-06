package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName

data class FavData2(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("product")
    val product: Product? = Product()
)