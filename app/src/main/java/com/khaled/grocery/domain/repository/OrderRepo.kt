package com.khaled.grocery.domain.repository

import android.util.Log
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.OrderDetails
import com.khaled.grocery.model.OrderSummaryData
import com.khaled.grocery.model.PlaceOrderItem
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepo @Inject constructor(
    private val apiService: ApiService
) {

    fun getOrdersData(): Flow<State<DataResponse<OrderSummaryData>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.fetchOrdersData()
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
                Log.i("OrderRepo", "getOrdersData: ${response.body()}")
            } else {
                Log.i("OrderRepo", "getOrdersData: ${response.body()}")
                emit(State.Fail(response.body()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown error"))
            Log.i("OrderRepo", "getOrdersData: ${e.message}")
        }
    }

    fun confirmOrder(placeOrderItem: PlaceOrderItem): Flow<State<DataResponse<OrderDetails>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.placeOrder(placeOrderItem)
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
            } else {
                emit(State.Fail(response.body()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown error"))
        }
    }
}
