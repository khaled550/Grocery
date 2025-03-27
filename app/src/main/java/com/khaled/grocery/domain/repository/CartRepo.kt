package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CartRepo @Inject constructor(
    private val apiService: ApiService
) {

    val cartItems : List<CartItem> = emptyList()

    fun fetchCartItems() : Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::getCartData)
    }

    fun getCartItems(): Flow<Response<DataResponse<CartData>>> = flow {
        val response: Response<DataResponse<CartData>> = apiService.getCartData()
        fetchCartItems()

        emit(response)
    }.flowOn(Dispatchers.IO)

    /*suspend fun updateCartItem(item: CartItem): Response<CartItem> {
        return apiService.updateCartItem(item)
    }*/
}
