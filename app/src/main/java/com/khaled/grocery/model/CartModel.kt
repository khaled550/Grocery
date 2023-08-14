package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class CartModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("quantity")
    val qty: Int? = null,
    @SerializedName("product")
    val product: Product? = null,
)
