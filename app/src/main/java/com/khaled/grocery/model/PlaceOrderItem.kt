package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class PlaceOrderItem(
    @SerializedName("address_id")
    val addressId: Int,

    @SerializedName("payment_method")
    val paymentMethod: Int,

    @SerializedName("use_points")
    val usePoints: Boolean,
)
