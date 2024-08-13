package com.test.km.di

import android.content.Context
import com.test.km.data.UsersRepository
import com.test.km.data.api.ApiConfig
import com.test.km.data.api.ApiService

object Injection {
    fun provideRepository(): UsersRepository {
        val apiService = ApiConfig.getApiService()
        return UsersRepository(apiService)
    }
}