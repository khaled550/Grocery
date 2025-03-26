package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("quantity")
    var quantity: Int? = null,
    @SerializedName("product")
    val product: Product? = null,
)
