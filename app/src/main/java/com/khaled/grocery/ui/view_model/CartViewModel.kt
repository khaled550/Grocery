package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.model.CartData
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

    private fun getCartData() {
        viewModelScope.launch {
            repository.fetchCartItems()
                .onStart { cartItems.value = State.Loading }
                .collect{
                    cartItems.postValue(it)
                }
        }
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch {
            //repository.updateCartItemQuantity(itemId, newQuantity)
        }
    }

    fun removeItem(itemId: Int) {
        viewModelScope.launch {
            //repository.removeItemFromCart(itemId)
        }
    }
}
