package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.LoginRequest
import com.khaled.grocery.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepo {

    /*suspend fun login(username: String, password: String): Flow<LoginResult> = flow {
        try {
            // Make the API call
            val response = fetchRegistr

            if (response.status) {
                // Emit the result if login is successful
                emit(LoginResult.Success(response.data))
            } else {
                // Emit an error if login failed
                emit(LoginResult.Error(response.message))
            }
        } catch (e: HttpException) {
            // Emit network errors
            emit(LoginResult.Error("Network error: ${e.message}"))
        } catch (e: IOException) {
            // Emit no internet connection error
            emit(LoginResult.Error("No internet connection"))
        }
    }*/
}


class AuthRepository @Inject constructor(private val apiService: ApiService) {

    fun login(email: String, password: String): Flow<MyResult<String>> = flow {
        emit(MyResult.Loading) // Show loading state

        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.data.token // Extract token
                emit(MyResult.Success(token)) // Return only the token
            } else {
                emit(MyResult.Error("Login failed"))
            }
        } catch (e: Exception) {
            emit(MyResult.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}


sealed class MyResult<out T> {
    object Loading : MyResult<Nothing>()
    data class Success<T>(val data: T) : MyResult<T>()
    data class Error(val message: String) : MyResult<Nothing>()
}
