package com.khaled.grocery.api

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.khaled.grocery.utils.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MyInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Fetch the token synchronously
        val authToken = runBlocking {
            userPreferences.authToken.firstOrNull().also { token ->
                Log.d("AuthToken", "Retrieved Token: $token")
            } ?: ""
        }

        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("lang", "ar")
            .apply {
                if (authToken.isNotEmpty()) {
                    addHeader("Authorization", authToken)
                }
            }
            .build()

        return chain.proceed(request)
    }
}

