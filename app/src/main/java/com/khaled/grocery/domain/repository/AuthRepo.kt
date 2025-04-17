package com.khaled.grocery.domain.repository

import LoginRequest
import SignUpRequest
import User
import android.util.Log
import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.DataResponse
import com.khaled.grocery.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepo @Inject constructor(private val apiService: ApiService) {

    fun login(email: String, password: String): Flow<State<DataResponse<User>>> = flow {
        emit(State.Loading) // Show loading state
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.data?.token // Extract token
                emit(State.Success(response.body()!!)) // Return only the token
            } else {
                emit(State.Fail("Login failed"))
            }
        } catch (e: Exception) {
            emit(State.Fail(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun signUp(signUpRequest: SignUpRequest): Flow<State<DataResponse<User>>> = flow {
        emit(State.Loading) // Show loading state
        try {
            val response = apiService.registerUser(signUpRequest)
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.data?.token // Extract token
                emit(State.Success(response.body()!!)) // Return only the token
            } else {
                emit(State.Fail("Sign up failed"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error during sign up: ${e.localizedMessage}")
        }
    }
}
