package com.khaled.grocery.repository

import com.khaled.grocery.domain.RetrofitHelper
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepo {

    suspend fun fetchHomeProducts() : Flow<State<List<Product?>>> {
        return flow {
            emit(State.Loading)
            val result = RetrofitHelper.api.getHomeData()
            if (result.isSuccessful && result.body() != null){
                emit(State.Success(data = result.body()!!.data!!.products!!))
            } else{
                emit(State.Fail(result.message()))
            }
        }
    }
}
