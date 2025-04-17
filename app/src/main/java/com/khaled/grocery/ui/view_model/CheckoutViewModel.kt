package com.khaled.grocery.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AddressRepo
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.domain.repository.OrderRepo
import com.khaled.grocery.model.AddressData
import com.khaled.grocery.model.AddressData.Address
import com.khaled.grocery.model.CartData
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.OrderDetails
import com.khaled.grocery.model.PlaceOrderItem
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepo: CartRepo,
    private val orderRepo: OrderRepo,
    private val addressRepo: AddressRepo
) : ViewModel() {

    private val _cartItems = MutableStateFlow<State<DataResponse<CartData>>>(State.Loading)
    val cartData: StateFlow<State<DataResponse<CartData>>> = _cartItems

    private val _checkoutState = MutableStateFlow<State<DataResponse<OrderDetails>>>(State.Loading)
    val checkoutState: StateFlow<State<DataResponse<OrderDetails>>> = _checkoutState

    private val _defaultAddressId = MutableStateFlow<Int?>(null)
    private val defaultAddressId: StateFlow<Int?> = _defaultAddressId
    //lateinit var address: State<Address>

    private val _address = MutableStateFlow<State<Address>>(State.Loading)
    val address: StateFlow<State<Address>> = _address

    init {
        loadDefaultAddress()
        loadCartItems()
    }

    private fun loadDefaultAddress() {
        viewModelScope.launch {
            addressRepo.getDefaultAddressId().collect {
                _defaultAddressId.value = it
                addressRepo.getAddressById(it ?: 0).collectLatest { addressState ->
                    _address.value = addressState
                    Log.i("CheckoutViewModel", "Default address: $addressState")
                }
            }
        }
    }

    fun checkout(paymentMethod: Int) {
        viewModelScope.launch {
            try {
                _checkoutState.value = State.Loading
                orderRepo.confirmOrder(
                    PlaceOrderItem(
                        addressId = defaultAddressId.value?: 0,
                        paymentMethod = paymentMethod,
                        usePoints = false,
                    )
                ).collectLatest { result ->
                    _checkoutState.value = result
                    Log.i("CheckoutViewModel", "checkout result: $result")
                }
            } catch (e: Exception) {
                _checkoutState.value = State.Fail(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            cartRepo.getCartItems().collectLatest {
                _cartItems.value = it
            }
        }
    }
}
