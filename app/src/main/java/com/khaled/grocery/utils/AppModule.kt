package com.khaled.grocery.utils;

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

// Provide Application Context
@Provides
@Singleton
fun provideContext(@ApplicationContext context: Context): Context {
    return context
}

// Provide UserPreferences instance
@Provides
@Singleton
fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
    return UserPreferences(context)
}
}
