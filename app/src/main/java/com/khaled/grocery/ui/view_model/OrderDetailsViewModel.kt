package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.OrderRepo
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.OrderDetails
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val repo: OrderRepo
) : ViewModel() {

    private val _orderDetails = MutableStateFlow<State<DataResponse<OrderDetails>>>(State.Loading)
    val orderDetails = _orderDetails.asStateFlow()

    fun loadOrderDetails(orderId: Int) {
        viewModelScope.launch {
            repo.getOrderDetails(orderId).collect {
                _orderDetails.value = it
            }
        }
    }
}
