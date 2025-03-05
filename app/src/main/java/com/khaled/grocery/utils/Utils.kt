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
    public

    companion object {
        fun <T> convertToFlow(function: suspend () -> Response<T>): Flow<State<T?>> {
            return flow {
                emit(State.Loading)
                val result = function()
                if (result.isSuccessful && result.body() != null){
                    Log.i("toFlow", result.body()!!.toString())
                    emit(State.Success(data = result.body()!!))
                } else{
                    emit(State.Fail(result.message()))
                }
            }.catch { e ->
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