package com.khaled.grocery.api

import User
import com.khaled.grocery.model.AddCartItem
import com.khaled.grocery.model.AddressData.Address
import com.khaled.grocery.model.AddressData
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.LoginRequest
import com.khaled.grocery.model.LoginResponse
import com.khaled.grocery.model.OrderSummaryData
import com.khaled.grocery.model.OrderDetails
import com.khaled.grocery.model.PlaceOrderItem
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("home")
    suspend fun getHomeData() : Response<DataResponse<HomeData>>

    // cart section
    @GET("carts")
    suspend fun getCartData() : Response<DataResponse<CartData>>

    @PUT("carts/{id}")
    suspend fun updateCartItem(
        @Path("id") itemId: Int,  // Pass item ID in URL
        @Body quantity: Map<String, Int> // Send only quantity as JSON
    ): Response<DataResponse<CartItem>>

    @DELETE("carts/{id}")
    suspend fun removeCartItem(
        @Path("id") itemId: Int,
    ): Response<DataResponse<CartItem>>

    @POST("carts")
    suspend fun addCartItem(
        @Body itemId: Map<String, Int>,  // Pass item ID in URL 52 55 53
    ): Response<DataResponse<AddCartItem>>

    // orders section
    @GET("orders")
    suspend fun fetchOrdersData(): Response<DataResponse<OrderSummaryData>>

    //@GET("orders/{id}")

    @POST("orders")
    suspend fun placeOrder(
        @Body placeOrderItem: PlaceOrderItem
    ): Response<DataResponse<OrderDetails>>

    // favorites section
    @GET("favorites")
    suspend fun getFavData() : Response<DataResponse<FavData>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): Response<DataResponse<Product>>

    @POST("register")
    suspend fun registerUser() : Response<DataResponse<User>>

    @GET("profile")
    suspend fun getUserData(): Response<DataResponse<User>>

    @GET("addresses")
    suspend fun getAllAddresses(): Response<DataResponse<AddressData>>

    @POST("addresses")
    suspend fun addAddress(@Body address: Address): Response<DataResponse<Address>>

    @PUT("addresses/{id}")
    suspend fun updateAddress(
        @Path("id") addressId: Int,
        @Body address: Address
    ): Response<DataResponse<Address>>

    @DELETE("addresses/{id}")
    suspend fun deleteAddress(
        @Path("id") addressId: Int
    ): Response<DataResponse<Address>>
}
