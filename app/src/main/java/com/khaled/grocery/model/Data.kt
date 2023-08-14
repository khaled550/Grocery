package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ad")
    val ad: String? = null,
    @SerializedName("banners")
    val banners: List<Banner?>? = null,
    @SerializedName("products")
    val products: List<Product?>? = null
)