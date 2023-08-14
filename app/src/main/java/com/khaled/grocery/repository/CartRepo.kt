package com.khaled.grocery.repository

import android.util.Log
import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartModel
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepo {

    suspend fun fetchCartItems() : Flow<State<List<CartModel>>>{
        return flow {
            emit(State.Loading)
            val result = RetrofitHelper.api.getCartData()
            if (result.isSuccessful && result.body() != null){
                Log.i("fetchCartItems", result.body()!!.toString())
                var data = result.body()!!.data as CartData
                emit(State.Success(data = data.cartItems!!))
            } else{
                emit(State.Fail(result.message()))
            }
        }
    }
}