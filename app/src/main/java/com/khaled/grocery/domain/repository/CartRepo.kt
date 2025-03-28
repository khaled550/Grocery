package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CartRepo @Inject constructor(
    private val apiService: ApiService
) {

    fun getCartItems() : Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlow(apiService::getCartData)
    }

    fun updateCartItem(item: CartItem): Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlowWithArgs { apiService.updateCartItem(item) }
    }
}
