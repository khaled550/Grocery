package com.khaled.grocery.domain.repository

import User
import android.util.Log
import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartModel
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.FavData2
import com.khaled.grocery.model.HomeData
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

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

    fun fetchRegister() : Flow<State<DataResponse<User>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::registerUser)
    }
}
