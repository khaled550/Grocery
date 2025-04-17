package com.khaled.grocery.ui.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.DetailsRepo
import com.khaled.grocery.model.AddCartItem
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.Product
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepo
) : ViewModel() {

    private val _productDetails = MutableStateFlow<State<DataResponse<Product>?>>(State.Loading)
    val productDetails: StateFlow<State<DataResponse<Product>?>> = _productDetails

    private val _cartResponse = MutableStateFlow<State<DataResponse<AddCartItem>>>(State.Loading)
    val cartResponse: StateFlow<State<DataResponse<AddCartItem>>> = _cartResponse

    fun getProductDetails(productId: Int) {
        _productDetails.value = State.Loading
        // data fetch
        viewModelScope.launch {
            repository.getProductDetails(productId).collectLatest { state ->
                // Update _productDetails once you have the new data
                _productDetails.value = state
            }
        }
    }

    private fun addOrRemoveCartItem(itemId: Int) {
        Log.i("addOrRemoveCartItem", "adding")
        viewModelScope.launch {
            repository.addOrRemoveCartItem(itemId)
                .catch { e ->
                    Log.e("DetailsVM", "Error: ${e.message}")
                }
                .collect { response ->
                    _cartResponse.value = response
                    Log.i("addOrRemoveCartItem", "response: ${response.toData()?.data}")
                }
        }
    }

    fun toggleCartBtn(itemId: Int) {
        // Check if the item is already in the cart
        val isInCart = _productDetails.value is State.Success && (_productDetails.value as State.Success).data?.data?.inCart == true
        // Toggle the cart item
        addOrRemoveCartItem(itemId)
        // Update the UI state
        _productDetails.value = if (isInCart) {
            State.Success(DataResponse(data = _productDetails.value.toData()?.data?.copy(inCart = false)))
        } else {
            State.Success(DataResponse(data = _productDetails.value.toData()?.data?.copy(inCart = true)))
        }
        Log.i("toggleCartBtn", "isInCart: $isInCart, itemId: $itemId")
    }
}
