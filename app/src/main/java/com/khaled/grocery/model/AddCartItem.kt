package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class AddCartItem(
    @SerializedName("id") val id: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("product") val cartProduct: CartProduct
) {
    data class CartProduct(
        @SerializedName("id") val id: Int,
        @SerializedName("price") val price: Int,
        @SerializedName("old_price") val oldPrice: Int,
        @SerializedName("discount") val discount: Int,
        @SerializedName("image") val image: String,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String
    )
}