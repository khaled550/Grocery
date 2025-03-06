package com.khaled.grocery.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.khaled.grocery.domain.repository.LoginRepo
import com.khaled.grocery.domain.repository.LoginResult
import com.khaled.grocery.domain.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: MainRepo
) : ViewModel(){

    private val _loginResult = MutableStateFlow<LoginResult>(LoginResult.Success)
    val loginResult: StateFlow<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repo.fetchRegister(username, password).collect { result ->
                _loginResult.value = result.toData()
            }
        }
    }
}
