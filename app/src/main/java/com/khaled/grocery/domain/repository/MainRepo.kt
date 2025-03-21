package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow

class MainRepo {

    fun fetchHomeProducts() : Flow<State<DataResponse<HomeData>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::getHomeData)
    }

    fun fetchCartItems() : Flow<State<DataResponse<CartData>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::getCartData)
    }

    fun fetchFavItems() : Flow<State<DataResponse<FavData>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::getFavData)
    }

    /*fun fetchRegister(username: String, password: String) : Flow<State<DataResponse<SignUpResponse>?>> {
        return Utils.convertToFlow{
            //RetrofitHelper.api.login()
        }
    }*/
}
