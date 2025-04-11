package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.OrderRepo
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.OrderSummaryData
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepo
) : ViewModel() {

    private val _orders = MutableStateFlow<State<DataResponse<OrderSummaryData>>>(State.Loading)
    val orders: StateFlow<State<DataResponse<OrderSummaryData>>> = _orders

    init {
        getAllOrders()
    }

    fun getAllOrders() {
        viewModelScope.launch {
            repository.getOrdersData().collect {
                _orders.value = it
            }
        }
    }
}
