package com.khaled.grocery.domain.repository

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.model.LoginRequest
import com.khaled.grocery.model.LoginResponse
import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
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

    fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            emit(Result.success(apiService.login(LoginRequest(email, password))))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("Login failed: ${e.message}")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An error occurred: ${e.message}")))
        }
    }
}


sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}
