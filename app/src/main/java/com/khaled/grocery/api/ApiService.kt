package com.khaled.grocery.api

import User
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.LoginRequest
import com.khaled.grocery.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("home")
    suspend fun getHomeData() : Response<DataResponse<HomeData>>

    @GET("carts")
    suspend fun getCartData() : Response<DataResponse<CartData>>

    @GET("favorites")
    suspend fun getFavData() : Response<DataResponse<FavData>>

    @POST("register")
    suspend fun registerUser() : Response<DataResponse<User>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}