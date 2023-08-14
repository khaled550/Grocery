package com.khaled.grocery.model


import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("ad")
    val ad: String? = null,
    @SerializedName("banners")
    val banners: List<Banner>? = null,
    @SerializedName("products")
    val products: List<Product>? = null
)