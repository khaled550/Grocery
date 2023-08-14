package com.khaled.grocery.domain

import com.khaled.grocery.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("home")
    suspend fun getHomeData() : Response<HomeResponse>
}