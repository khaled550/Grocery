package com.khaled.grocery.api

import com.khaled.grocery.utils.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MyInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var authToken: String? = null
        runBlocking {
            authToken = userPreferences.authToken.toString() // Get token synchronously
        }

        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("lang", "ar")
            .addHeader("Authorization", "$authToken")
            .build()

        return chain.proceed(request)
    }
}

