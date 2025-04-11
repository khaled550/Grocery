package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AddressRepo
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.domain.repository.OrderRepo
import com.khaled.grocery.model.CartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.OrderDetails
import com.khaled.grocery.model.PlaceOrderItem
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val addressRepo: AddressRepo
) : ViewModel() {

    private val _checkoutState = MutableStateFlow<State<DataResponse<OrderDetails>>>(State.Loading)
    val checkoutState: StateFlow<State<DataResponse<OrderDetails>>> = _checkoutState

    private val _defaultAddressId = MutableStateFlow<Int?>(null)
    private val defaultAddressId: StateFlow<Int?> = _defaultAddressId

    init {
        loadDefaultAddressId()
    }

    private fun loadDefaultAddressId() {
        viewModelScope.launch {
            addressRepo.getDefaultAddressId().collect {
                _defaultAddressId.value = it
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            try {
                _checkoutState.value = State.Loading
                orderRepo.confirmOrder(
                    PlaceOrderItem(
                        addressId = defaultAddressId.value?: 0,
                        paymentMethod = 1,
                        usePoints = false,
                    )
                ).collectLatest { result ->
                    _checkoutState.value = result
                }
            } catch (e: Exception) {
                _checkoutState.value = State.Fail(e.message ?: "Unknown error")
            }
        }
    }
}