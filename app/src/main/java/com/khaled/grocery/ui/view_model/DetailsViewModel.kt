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

    // LiveData to hold product details
    private val _productDetails = MutableLiveData<State<DataResponse<Product>?>>()
    val productDetails: MutableLiveData<State<DataResponse<Product>?>> = _productDetails

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

    fun addOrRemoveCartItem(itemId: Int, onComplete: () -> Unit) {

        Log.i("addOrRemoveCartItem", "adding")
        viewModelScope.launch {
            repository.addOrRemoveCartItem(itemId)
                .catch { e ->
                    Log.e("DetailsVM", "Error: ${e.message}")
                }
                .collect { response ->
                    _cartResponse.value = response
                }
        }
        onComplete() // Re-enable UI buttons
    }

    /*fun removeCartItem(itemId: Int, onComplete: () -> Unit) {
        Log.i("addOrRemoveCartItem", "removing")
        viewModelScope.launch {
            repository.addCartItem(itemId).collectLatest { result ->
                if (result is State.Success) {
                    getProductDetails(itemId) // Refresh product details after adding/removing
                } else
                    onComplete() // Re-enable UI buttons
            }
        }
    }*/

    fun checkCartItem(): Boolean {
        // Check if the product is already in the cart
        if (productDetails.value != null) {
            return productDetails.value?.toData()?.data?.inCart!!
        }
        return false
    }
}
