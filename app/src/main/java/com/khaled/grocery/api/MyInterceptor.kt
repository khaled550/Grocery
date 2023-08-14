package com.khaled.grocery.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("lang", "ar")
            .addHeader("Authorization", "1J9qahovmkk18cMq9vC2nJOV5YHFdby2ZN2cc9jHWbS8yjNwfsd43AqRbtNvo7uMcJcdj9")
            .build()
        return chain.proceed(request)
    }

}