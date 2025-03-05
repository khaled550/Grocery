package com.khaled.grocery.ui.view_model

import User
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.domain.repository.SignUpRepo
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.FavData
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: SignUpRepo
) : ViewModel(){

    val userState = MutableLiveData<State<DataResponse<User>?>>()


}
