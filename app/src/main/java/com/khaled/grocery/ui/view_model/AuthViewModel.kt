package com.khaled.grocery.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AuthRepository
import com.khaled.grocery.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repo.login(email, password).collect { result ->
                _loginResult.value = result
            }
        }
    }
}
