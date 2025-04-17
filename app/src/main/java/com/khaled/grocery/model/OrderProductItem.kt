package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class OrderProductItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("price")
    val price: Double,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String
)
