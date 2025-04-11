package com.khaled.grocery.model

import com.google.gson.annotations.SerializedName

data class OrderSummaryData(
    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("data")
    val orderSummaryItems: List<OrderSummaryItem>,

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
    data class OrderSummaryItem(
        @SerializedName("id")
        val id: Int,

        @SerializedName("date")
        val date: String?,

        @SerializedName("total")
        val total: Double?,

        @SerializedName("status")
        val status: String?
    )

}
