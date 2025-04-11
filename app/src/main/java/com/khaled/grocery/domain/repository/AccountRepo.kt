package com.khaled.grocery.domain.repository

import User
import android.util.Log
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepo @Inject constructor(
    private val apiService: ApiService
) {
    fun getUserData(): Flow<State<DataResponse<User>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.getUserData()
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
            } else {
                emit(State.Fail("Error: ${response.message()}"))
                Log.e("CartRepo", "Error fetching cart items: ${response.message()}")
            }
        } catch (e: Exception) {
            emit(State.Fail("Exception: ${e.message}"))
        }
    }
}
