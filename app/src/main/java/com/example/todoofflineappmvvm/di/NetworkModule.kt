package com.example.todoofflineappmvvm.di

import com.example.todoofflineappmvvm.data.remote.api.ToDoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()) // o Gson
            .build()

    @Provides
    @Singleton
    fun provideToDoApiService(retrofit: Retrofit): ToDoApiService =
        retrofit.create(ToDoApiService::class.java)
}
