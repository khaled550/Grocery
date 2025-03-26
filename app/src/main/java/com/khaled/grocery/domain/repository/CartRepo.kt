package com.khaled.grocery.domain.repository

import com.khaled.grocery.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow

class CartRepo {
    private val cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    fun getCartItems(): Flow<List<CartItem>> = cartItems

    suspend fun updateCartItemQuantity(itemId: Int, newQuantity: Int) {
        cartItems.emit(cartItems.value.map { item ->
            if (item.id == itemId) item.copy(quantity = newQuantity) else item
        })
    }

    suspend fun addItemToCart(item: CartItem) {
        cartItems.emit(cartItems.value + item)
    }

    suspend fun removeItemFromCart(itemId: Int) {
        cartItems.emit(cartItems.value.filterNot { it.id == itemId })
    }
}
