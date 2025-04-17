package com.khaled.grocery.domain.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.AddressData
import com.khaled.grocery.model.AddressData.Address
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import com.khaled.grocery.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepo @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    fun getAllAddresses(): Flow<State<DataResponse<AddressData>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.getAllAddresses()
            emit(State.Success(response.body()!!))
        } catch (e: Exception) {
            emit(State.Fail(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun getAddressById(addressId: Int): Flow<State<Address>> {
        /*var result: State<Address> = State.Loading
        getAllAddresses().collect { state ->
            result = when (state) {
                is State.Success -> {
                    val address = state.data.data?.addresses?.find { it.id == addressId }
                    if (address != null) {
                        State.Success(address)
                    } else {
                        State.Fail("Address not found")
                    }
                }
                is State.Fail -> {
                    State.Fail(state.message)
                }
                else -> State.Loading
            }
        }*/
        return flow {
            emit(State.Loading)
            try {
                val response = apiService.getAllAddresses()
                val address = response.body()?.data?.addresses?.find { it.id == addressId }
                if (address != null) {
                    emit(State.Success(address))
                } else {
                    emit(State.Fail("Address not found"))
                }
            } catch (e: Exception) {
                emit(State.Fail(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    fun addAddress(address: Address): Flow<State<DataResponse<Address>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.addAddress(address)
            emit(State.Success(response.body()!!))
        } catch (e: Exception) {
            emit(State.Fail(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun updateAddress(address: Address): Flow<State<DataResponse<Address>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.updateAddress(address.id!!, address)
            emit(State.Success(response.body()!!))
        } catch (e: Exception) {
            emit(State.Fail(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun deleteAddress(addressId: Int): Flow<State<DataResponse<Address>>> = flow {
        emit(State.Loading)
        try {
            val response = apiService.deleteAddress(addressId)
            emit(State.Success(response.body()!!))
        } catch (e: Exception) {
            emit(State.Fail(e.localizedMessage ?: "Unknown error"))
        }
    }

    suspend fun saveDefaultAddressId(id: Int) {
        userPreferences.saveDefaultAddress(id)
    }

    fun getDefaultAddressId(): Flow<Int?> {
        return userPreferences.defaultAddressId
    }
}
