package com.khaled.grocery.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.model.CartModel
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import com.khaled.grocery.repository.CartRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repo: CartRepo = CartRepo()

    val cartItems = MutableLiveData<State<List<CartModel>>>()

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