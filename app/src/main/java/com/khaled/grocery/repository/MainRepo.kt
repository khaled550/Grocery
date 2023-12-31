package com.khaled.grocery.repository

import android.util.Log
import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepo {

    suspend fun fetchHomeProducts() : Flow<State<List<Product>>> {
        return flow {
            emit(State.Loading)
            val result = RetrofitHelper.api.getHomeData()
            if (result.isSuccessful && result.body() != null){
                Log.i("fetchHomeProducts", result.body()!!.toString())
                var data = result.body()!!.data as HomeData
                emit(State.Success(data = data.products!!))
            } else{
                emit(State.Fail(result.message()))
            }
        }
    }
}
