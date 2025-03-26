package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AuthRepository
import com.khaled.grocery.domain.repository.MyResult
import com.khaled.grocery.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<MyResult<String>?>(null)
    val loginState: StateFlow<MyResult<String>?> = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<MyResult<Boolean>?>(null)
    val logoutState: StateFlow<MyResult<Boolean>?> = _logoutState.asStateFlow()

    // Function to login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = MyResult.Loading

            authRepository.login(email, password).collect { result ->
                if (result is MyResult.Success) {
                    userPreferences.saveAuthToken(result.data) // Save token in DataStore
                }
                _loginState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAuthToken()
            _logoutState.value = MyResult.Success(true)
        }
    }

    // Function to retrieve the auth token as a Flow
    val authToken: Flow<String?> = userPreferences.authToken
}
