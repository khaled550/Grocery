package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class OrderDetails(
    @SerializedName("id")
    val id: Int,

    @SerializedName("cost")
    val cost: Double,

    @SerializedName("discount")
    val discount: Int,

    @SerializedName("points")
    val points: Int,

    @SerializedName("vat")
    val vat: Double,

    @SerializedName("total")
    val total: Double,

    @SerializedName("points_commission")
    val pointsCommission: Int,

    @SerializedName("promo_code")
    val promoCode: String,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("address")
    val address: AddressData.Address? = AddressData.Address(0, "", "", "", "", null, 0.0, 0.0),

    @SerializedName("products")
    val products: List<Product> = emptyList()
)
