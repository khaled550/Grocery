package com.khaled.grocery.di

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
    fun provideMainRepo(): MainRepo {
        return MainRepo()
    }

    @Provides
    fun provideLoginRepo(): LoginRepo {
        return LoginRepo()
    }
}