package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class CartData(
    @SerializedName("total")
    val total: Double? = null,
    @SerializedName("sub_total")
    val sub_total: Double? = null,
    @SerializedName("cart_items")
    val cartItems: List<CartItem>? = null
)