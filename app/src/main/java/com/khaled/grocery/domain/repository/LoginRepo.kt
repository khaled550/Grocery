package com.khaled.grocery.domain.repository

import com.khaled.grocery.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginRepo {

    suspend fun login(username: String, password: String): Flow<LoginResult> = flow {
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
    }
}

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}
