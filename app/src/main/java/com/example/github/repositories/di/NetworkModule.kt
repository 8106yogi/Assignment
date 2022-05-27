package com.example.github.repositories.di

import com.example.github.repositories.network.GitHubEndpoints
import com.example.github.repositories.util.GITHUB_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun providesRetrofitService(retrofit: Retrofit): GitHubEndpoints {
        return retrofit.create(GitHubEndpoints::class.java)
    }


}