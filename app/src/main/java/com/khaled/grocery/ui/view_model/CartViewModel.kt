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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepo
) : ViewModel() {

    private val _cartState = MutableLiveData<State<DataResponse<CartData>?>>()
    val cartState: MutableLiveData<State<DataResponse<CartData>?>> = _cartState

    fun loadCartItems() {
        viewModelScope.launch {
            repository.getCartItems().collectLatest { _cartState.value = it }
        }
    }

    fun updateCartItem(item: CartItem, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.updateCartItem(item).collectLatest { result ->
                if (result is State.Success) {
                    loadCartItems() // Refresh the cart after update
                }
                onComplete()
            }
        }
    }

    fun deleteCartItem(itemId: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.deleteCartItem(itemId).collectLatest { result ->
                if (result is State.Success) {
                    loadCartItems() // Refresh cart
                } else
                onComplete() // Re-enable UI buttons
            }
        }
    }
}
