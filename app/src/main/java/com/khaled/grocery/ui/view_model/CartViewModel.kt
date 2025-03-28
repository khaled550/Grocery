package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.onStart

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepo
) : ViewModel() {

    val cartItems = MutableLiveData<State<DataResponse<CartData>?>>()

    init {
        getCartData()
    }

    private fun getCartData() {
        viewModelScope.launch {
            repository.getCartItems()
                .onStart { cartItems.value = State.Loading }
                .collect{
                    cartItems.postValue(it)
                }
        }
    }

    fun updateQuantity(item: CartItem) {
        viewModelScope.launch {
            repository.updateCartItem(item)
                .onStart { cartItems.value = State.Loading }
                .collect {
                    cartItems.postValue(it)
                }
        }
    }

    fun removeItem(itemId: Int) {
        viewModelScope.launch {
            //repository.removeItemFromCart(itemId)
        }
    }
}
