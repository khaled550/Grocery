package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import com.khaled.grocery.domain.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: MainRepo
): ViewModel() {

    val cartItems = MutableLiveData<State<DataResponse<CartData>?>>()

    init {
        getCartItems()
    }

    private fun getCartItems(){
        viewModelScope.launch {
            repo.fetchCartItems().collect{
                cartItems.postValue(it)
            }
        }
    }
}
