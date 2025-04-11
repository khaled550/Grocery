package com.khaled.grocery.utils

import android.util.Log
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class Utils {

    companion object {
        fun <T> convertToFlow(function: suspend () -> Response<T>): Flow<State<T?>> {
            return flow {
                emit(State.Loading) // Emit loading state
                val result = function() // Execute the API call
                if (result.isSuccessful && result.body() != null) {
                    Log.i("toFlow", result.message())
                    emit(State.Success(data = result.body()!!)) // Emit success state
                } else {
                    emit(State.Fail(result.message())) // Emit failure state
                    Log.i("toFlow", result.message())
                }
            }.catch { e ->
                // Handle exceptions
                val errorMessage = when (e) {
                    is SocketTimeoutException -> "Request timed out. Please try again."
                    is IOException -> "Network error. Check your connection."
                    else -> "An unexpected error occurred."
                }
                Log.i("toFlow", e.toString())
                emit(State.Fail(errorMessage)) // Emit failure state with error message
            }
        }

        fun <T> convertToFlowWithArgs(function: suspend () -> Response<T>): Flow<State<T?>> {
            return flow {
                emit(State.Loading) // Emit loading state
                try {
                    val result = function() // Execute the provided function
                    if (result.isSuccessful && result.body() != null) {
                        Log.i("toFlow", result.body()!!.toString())
                        emit(State.Success(data = result.body()!!)) // Emit success with the result
                    } else {
                        emit(State.Fail(result.message())) // Emit failure with error message
                    }
                } catch (e: Exception) {
                    // Handle exceptions
                    when (e) {
                        is SocketTimeoutException -> emit(State.Fail("Request timed out. Please try again."))
                        is IOException -> emit(State.Fail("Network error. Check your connection."))
                        else -> emit(State.Fail("An unexpected error occurred."))
                    }
                }
            }
        }
    }
}
