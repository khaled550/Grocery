package com.khaled.grocery.utils

import android.util.Log
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

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
            }
        }
    }
}