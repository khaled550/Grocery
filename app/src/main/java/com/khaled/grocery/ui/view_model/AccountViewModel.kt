package com.khaled.grocery.ui.view_model

import User
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AccountRepo
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepo: AccountRepo
) : ViewModel() {

    private val _userData = MutableLiveData<State<DataResponse<User>>>(State.Loading)
    val userData: MutableLiveData<State<DataResponse<User>>> = _userData

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            accountRepo.getUserData().collectLatest { state ->
                _userData.value = state
            }
        }
    }

}