package com.khaled.grocery.di

import com.khaled.grocery.api.ApiService
import com.khaled.grocery.domain.repository.AccountRepo
import com.khaled.grocery.domain.repository.AddressRepo
import com.khaled.grocery.domain.repository.AuthRepo
import com.khaled.grocery.domain.repository.CartRepo
import com.khaled.grocery.domain.repository.DetailsRepo
import com.khaled.grocery.domain.repository.MainRepo
import com.khaled.grocery.domain.repository.OrderRepo
import com.khaled.grocery.utils.UserPreferences
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
    fun provideAuthRepo(apiService: ApiService): AuthRepo {
        return AuthRepo(apiService)
    }

    @Provides
    fun provideCartRepo(apiService: ApiService): CartRepo {
        return CartRepo(apiService)
    }

    @Provides
    fun provideDetailsRepo(apiService: ApiService): DetailsRepo {
        return DetailsRepo(apiService)
    }

    @Provides
    fun provideAccountRepo(apiService: ApiService): AccountRepo {
        return AccountRepo(apiService)
    }

    @Provides
    fun provideOrderRepo(apiService: ApiService): OrderRepo {
        return OrderRepo(apiService)
    }

    @Provides
    fun provideAddressRepo(apiService: ApiService, userPreferences: UserPreferences): AddressRepo {
        return AddressRepo(apiService, userPreferences)
    }
}
