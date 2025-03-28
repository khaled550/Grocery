package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepo @Inject constructor(
    private val apiService: ApiService
) {

    fun fetchHomeProducts() : Flow<State<DataResponse<HomeData>?>> {
        return Utils.convertToFlow(apiService::getHomeData)
    }

    fun fetchCartItems() : Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlow(apiService::getCartData)
    }

    fun fetchFavItems() : Flow<State<DataResponse<FavData>?>> {
        return Utils.convertToFlow(apiService::getFavData)
    }

}
