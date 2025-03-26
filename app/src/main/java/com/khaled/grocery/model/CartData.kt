package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class CartData(
    @SerializedName("total")
    val total: Int? = null,
    @SerializedName("sub_total")
    val sub_total: Int? = null,
    @SerializedName("cart_items")
    val cartItems: List<CartItem>? = null
)