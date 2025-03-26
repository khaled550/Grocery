package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepo
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = repository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateCartItemQuantity(itemId, newQuantity)
        }
    }

    fun removeItem(itemId: Int) {
        viewModelScope.launch {
            repository.removeItemFromCart(itemId)
        }
    }
}
