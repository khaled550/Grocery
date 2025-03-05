package com.khaled.grocery.domain.repository

import User
import com.khaled.grocery.api.RetrofitHelper
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow

class SignUpRepo {

    fun fetchSignUp() : Flow<State<DataResponse<User>?>> {
        return Utils.convertToFlow(RetrofitHelper.api::registerUser)
    }
}