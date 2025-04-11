package com.khaled.grocery.di

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.api.MyInterceptor
import com.khaled.grocery.utils.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val BASE_URL  = "https://student.valuxapps.com/api/"

    @Provides
    @Singleton
    fun provideInterceptor(userPreferences: UserPreferences): MyInterceptor {
        return MyInterceptor(userPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(myInterceptor: MyInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(myInterceptor)
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS) // Increase connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Increase read timeout
                .writeTimeout(30, TimeUnit.SECONDS)   // Increase write timeout
                .retryOnConnectionFailure(true)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
