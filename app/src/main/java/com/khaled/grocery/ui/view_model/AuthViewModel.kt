package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AuthRepository
import com.khaled.grocery.model.State
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

    private val _loginState = MutableStateFlow<State<String>?>(null)
    val loginState: StateFlow<State<String>?> = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<State<Boolean>?>(null)
    val logoutState: StateFlow<State<Boolean>?> = _logoutState.asStateFlow()

    // Function to login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = State.Loading

            authRepository.login(email, password).collect { result ->
                if (result is State.Success) {
                    userPreferences.saveAuthToken(result.data) // Save token in DataStore
                }
                _loginState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAuthToken()
            _logoutState.value = State.Success(true)
        }
    }

    // Function to retrieve the auth token as a Flow
    val authToken: Flow<String?> = userPreferences.authToken
}
