package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class AddressData(
    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("data")
    val addresses: List<Address>,

    @SerializedName("first_page_url")
    val firstPageUrl: String,

    @SerializedName("from")
    val from: Int?,

    @SerializedName("last_page")
    val lastPage: Int,

    @SerializedName("last_page_url")
    val lastPageUrl: String,

    @SerializedName("next_page_url")
    val nextPageUrl: String?,

    @SerializedName("path")
    val path: String,

    @SerializedName("per_page")
    val perPage: Int,

    @SerializedName("prev_page_url")
    val prevPageUrl: String?,

    @SerializedName("to")
    val to: Int?,

    @SerializedName("total")
    val total: Int
){
    data class Address(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("details")
        val details: String,
        @SerializedName("notes")
        val notes: String?,
        @SerializedName("latitude")
        val latitude: Double? = null,
        @SerializedName("longitude")
        val longitude: Double? = null
    )
}
