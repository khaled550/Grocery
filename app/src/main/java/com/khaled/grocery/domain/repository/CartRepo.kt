package com.khaled.grocery.domain.repository

import android.util.Log
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepo @Inject constructor(
    private val apiService: ApiService
) {
    fun getCartItems(): Flow<State<DataResponse<CartData>>> = flow {
        emit(State.Loading)
        try {
            val items = apiService.getCartData()
            emit(State.Success(items.body() ?: DataResponse()))
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown Error"))
            Log.e("CartRepo", "Error fetching cart items: ${e.message}")
        }
    }

    fun updateCartItem(item: CartItem): Flow<State<CartItem>> = flow {
        emit(State.Loading)
        try {
            val updatedItem = apiService.updateCartItem(item.id, item.quantity?.let { mapOf("quantity" to it) } ?: emptyMap())
            emit(State.Success(updatedItem.body()?.data ?: CartItem()))
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Update Failed"))
            Log.e("CartRepo", "Error updating cart item: ${e.message}")
        }
    }

    fun deleteCartItem(itemId: Int): Flow<State<CartItem>> = flow {
        try {
            val response = apiService.removeCartItem(itemId) // API call
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!.data!!)) // Item deleted
                Log.i("deleteCartItem", response.body()?.message.toString())
            } else {
                emit(State.Fail(response.body()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(State.Fail(e.message ?: "Unknown error"))
        }
    }

    /*fun getCartItems() : Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlow(apiService::getCartData)
    }

    fun updateCartItem(item: CartItem) : Flow<State<DataResponse<CartItem>?>> {
        return Utils.convertToFlowWithArgs { apiService.updateCartItem(
            item.id,
            item.quantity?.let { mapOf("quantity" to it) } ?: emptyMap()
        ) }.catch { e ->
            Log.e("UpdateCart", "Exception: ${e.localizedMessage}")
            emit(State.Fail("Unexpected error: ${e.localizedMessage}")
        ) }
    }*/

    /*fun updateCartItem(item: CartItem): Flow<State<DataResponse<CartItem>?>> {
        return flow {
            emit(State.Loading)
            try {
                val response = apiService.updateCartItem(item.id, item.quantity?.let { mapOf("quantity" to it) } ?: emptyMap())

                Log.d("UpdateCart", "Raw Response: $response")
                Log.d("UpdateCart", "Response Body: ${response.body()?.data}")

                if (response.isSuccessful && response.body() != null) {
                    emit(State.Success(response.body()))
                } else {
                    Log.e("UpdateCart", "Error: ${response.errorBody()?.string()}")
                    emit(State.Fail("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("UpdateCart", "Exception: ${e.localizedMessage}")
                emit(State.Fail("Unexpected error: ${e.localizedMessage}"))
            }
        }.flowOn(Dispatchers.IO)
    }
*/
}
