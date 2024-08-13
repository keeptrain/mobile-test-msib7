package com.test.km.di

import com.test.km.data.UsersRepository
import com.test.km.data.api.ApiConfig

object Injection {
    fun provideRepository(): UsersRepository {
        val apiService = ApiConfig.getApiService()
        return UsersRepository.getInstance(apiService)
    }
}