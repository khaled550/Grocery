package com.khaled.grocery.api

import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.HomeData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("home")
    suspend fun getHomeData() : Response<DataResponse<HomeData>>

    @GET("carts")
    suspend fun getCartData() : Response<DataResponse<CartData>>

    @GET("favorites")
    suspend fun getFavData() : Response<DataResponse<FavData>>
}