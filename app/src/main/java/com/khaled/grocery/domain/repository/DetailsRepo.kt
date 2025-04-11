package com.khaled.grocery.domain.repository

import android.util.Log
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.AddCartItem
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepo @Inject constructor(private val apiService: ApiService) {

    fun getProductDetails(productId: Int): Flow<State<DataResponse<Product>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.getProductDetails(productId)
            if (response.isSuccessful) {
                emit(State.Success(response.body() ?: DataResponse()))
            } else {
                emit(State.Fail("Failed to fetch product details"))
            }
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown error"))
        }
    }

    fun addOrRemoveCartItem(itemId: Int): Flow<State<DataResponse<AddCartItem>>> = flow {
        try {
            val response = apiService.addCartItem(mapOf("product_id" to itemId))
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
                Log.i("addCartItem", response.body()?.message!!.toString())
            } else {
                emit(State.Fail(response.body()?.message.toString()))
                Log.i("addCartItemFail", response.body()?.message.toString())
            }
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown error"))
            Log.i("addCartItemError", e.message.toString())
        }
    }
}
