package com.khaled.grocery.di

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.domain.repository.LoginRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideMainRepo(apiService: ApiService): MainRepo {
        return MainRepo(apiService)
    }

    @Provides
    fun provideLoginRepo(): LoginRepo {
        return LoginRepo()
    }

    @Provides
    fun provideCartRepo(apiService: ApiService): CartRepo {
        return CartRepo(apiService)
    }
}