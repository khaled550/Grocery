package com.khaled.grocery.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AddressRepo
import com.khaled.grocery.model.AddressData
import com.khaled.grocery.model.AddressData.Address
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repo: AddressRepo,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _addresses = MutableStateFlow<State<DataResponse<AddressData>>>(State.Loading)
    val addresses: StateFlow<State<DataResponse<AddressData>>> = _addresses

    private val _address = MutableStateFlow<State<DataResponse<Address>>>(State.Loading)
    val address: StateFlow<State<DataResponse<Address>>> = _address

    init {
        loadAddresses()
    }

    fun loadAddresses() {
        viewModelScope.launch {
            repo.getAllAddresses().collectLatest {
                _addresses.value = it
            }
        }
    }

    fun addAddress(address: Address, onComplete: () -> Unit) {
        viewModelScope.launch {
            repo.addAddress(address).collectLatest {
                _address.value = it
            }
            onComplete()
        }
    }

    fun updateAddress(address: Address, onComplete: () -> Unit) {
        viewModelScope.launch {
            repo.updateAddress(address).collectLatest {
                _address.value = it
            }
            onComplete()
        }
    }

    fun deleteAddress(addressId: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            repo.deleteAddress(addressId).collectLatest {
                _address.value = it
            }
            onComplete()
        }
    }

    val defaultAddressId = MutableStateFlow(0)

    fun setDefaultAddress(value: Int) {
        defaultAddressId.value = value
        viewModelScope.launch {
            userPreferences.saveDefaultAddress(value)
            Log.i("AddressViewModel", "Default address ID saved: $value")
        }
    }
}
