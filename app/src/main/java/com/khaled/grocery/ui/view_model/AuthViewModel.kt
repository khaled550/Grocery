package com.khaled.grocery.ui.view_model

import SignUpRequest
import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.AuthRepo
import com.khaled.grocery.model.DataResponse
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
    private val authRepository: AuthRepo,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val authToken: Flow<String?> = userPreferences.authToken
    private val _loginState = MutableStateFlow<State<DataResponse<User>>>(State.Loading)
    val loginState: StateFlow<State<DataResponse<User>>> = _loginState.asStateFlow()

    private val _signupState = MutableStateFlow<State<DataResponse<User>>>(State.Loading)
    val signupState: StateFlow<State<DataResponse<User>>> = _signupState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = State.Loading
            authRepository.login(email, password).collect { result ->
                if (result is State.Success) {
                    userPreferences.saveAuthToken(result.data.data!!.token) // Save token in DataStore
                }
                _loginState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAuthToken()
        }
    }

    fun signUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            _loginState.value = State.Loading
            authRepository.signUp(signUpRequest).collect { result ->
                if (result is State.Success) {
                    _signupState.value = result
                    userPreferences.saveAuthToken(result.data.data!!.token) // Save token in DataStore
                }
                _loginState.value = result
            }
        }
    }
}
